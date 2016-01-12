package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.logic.CalendarService;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.logic.HistoricalUnionChargeService;
import it.screwdrivers.payroll.logic.TimeCardService;
import it.screwdrivers.payroll.model.card.TimeCard;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;

import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named("contractorEngine")
@Stateless
public class ContractorPayEngine extends PayEngine {

	@Inject
	TimeCardService tc_service;

	@Inject
	HistoricalSalaryService hs_service;

	@Inject
	HistoricalUnionChargeService huc_service;

	@Inject
	CalendarService calendar_service;

	@Inject
	EmployeeDao e_dao;

	public ContractorPayEngine() {
		super();
	}

	@PostConstruct
	@Override
	public void initList() {
		setCon_employees(e_dao.findAllContractor());
	}

	@Override
	public void pay() {

		List<Date> working_days = calendar_service.lastWeekList();

		float dues = 0;
		float total_charges = 0;

		for (ContractorEmployee c : getCon_employees()) {

			float total = 0;

			if (c.getUnion() == null) {
				total = computeTotal(c, working_days);
				hs_service.registerPay(c, total);

			} else {
				total = computeTotal(c, working_days);
				dues = (c.getUnion().getUnion_dues()) * total;
				total_charges = huc_service
						.getLastMonthUnionTotalChargesByEmployee(c);
				hs_service.registerPay(c, total, (total_charges + dues));
			}
		}
	}

	@SuppressWarnings("deprecation")
	private float computeTotal(ContractorEmployee c, List<Date> working_days) {

		float total = 0;

		// this method return a list if timecard associated to the
		// given employee
		List<TimeCard> time_cards = tc_service.getByEmployee(c);

		// check if the timeCard has a date matching with
		// one of the date of the current week
		for (TimeCard tc : time_cards) {
			for (Date wd : working_days) {

				// check on the date field
				if (wd.getDate() == tc.getDate().getDate()
						&& wd.getMonth() == tc.getDate().getMonth()
						&& wd.getYear() == tc.getDate().getYear()) {

					// if it works extra hours
					// he will be paid 1,5 times his hourly rate
					// for each extra hour
					if (tc.getHours_worked() > 8) {

						// get extra hours
						float extra_hours = tc.getHours_worked() - 8;

						total += (c.getHourly_rate() * 8)
								+ (1.5 * c.getHourly_rate() * extra_hours);
					} else {
						total += (tc.getHours_worked() * c.getHourly_rate());
					}

					break;
				}
			}
		}

		return total;
	}
}
