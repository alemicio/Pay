package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.engine.utility.PayrollCalendar;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.logic.SalesCardService;
import it.screwdrivers.payroll.model.card.SalesCard;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named("commissionedEngine")
@Stateless
public class CommissionedPayEngine extends PayEngine {

	@Inject
	SalesCardService sc_service;

	@Inject
	HistoricalSalaryService hs_service;

	@Inject
	EmployeeDao e_dao;

	@Inject
	PayrollCalendar p_calendar;

	public CommissionedPayEngine() {
		super();
	}

	@PostConstruct
	@Override
	public void initList() {
		setCom_employees(e_dao.findAllCommissioned());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void pay() {

		List<SalesCard> s_cards;
		List<Date> working_days = p_calendar.last2WeeksList();
		float total = 0;

		for (CommissionedEmployee c : getCom_employees()) {
			s_cards = sc_service.retriveByEmployee(c);

			for (SalesCard s : s_cards) {
				for (Date wd : working_days) {
					if (wd.getDate() == s.getDate().getDate()
							&& wd.getMonth() == s.getDate().getMonth()
							&& wd.getYear() == s.getDate().getYear()) {

						total += c.getSale_rate() * s.getAmount();
					}
					break;
				}
			}

			hs_service.registerPay(c, total);
		}
	}
}
