package it.screwdrivers.payroll.engine;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.engine.utility.PayrollCalendar;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.logic.HistoricalUnionChargeService;
import it.screwdrivers.payroll.logic.TimeCardService;
import it.screwdrivers.payroll.model.card.TimeCard;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.Employee;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named("contractorEngine")
@Stateless
public class ContractorPayEngine extends PayEngine {

	@Inject
	EmployeeDao e_dao;
	@Inject
	TimeCardService t_controller;
	@Inject
	HistoricalSalaryService h_controller;
	@Inject
	PayrollCalendar p_calendar;
	@Inject
	HistoricalUnionChargeService huc_controller;

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

		List<Date> working_days = p_calendar.lastWeekList();
		float total = 0;
		float dues = 0;
		float total_charges = 0;

		for (ContractorEmployee c : getCon_employees()) {

			if (c.getUnion() == null) {

				total = payContractor(working_days, total, c);
				h_controller.registerPay(c, total);
			}
			else{
				
				total = payContractor(working_days, total, c);
				dues =  (c.getUnion().getUnion_dues() )* total;
				total_charges = huc_controller.UnionChargeByEmployee(c);
				
				h_controller.registerPay(c, total, (total_charges+dues) );
			}
		}

	}

	@SuppressWarnings("deprecation")
	private float payContractor(List<Date> working_days, float total,ContractorEmployee c) {
		
		float extra_hours;
		// this method return a list if timecard associated to the
		// given employee
		List<TimeCard> retrieved = t_controller.getByEmployee(c);

		// check if the timeCard has a date matching with
		// one of the date of the current week
		for (TimeCard t : retrieved) {
			
			System.out.println("id_timecard: "+t.getId());

			for (Date wd : working_days) {
				

				// check on the date field
				if (wd.getDate()  == t.getDate().getDate() &&
					wd.getMonth() == t.getDate().getMonth()&&
					wd.getYear()  == t.getDate().getYear()) {
					

						// if it works extra hours
						// he will be paid 1,5 times his hourly rate
						// fro each extra hour
						if (t.getHours_worked() > 8) {
							
							// get extra hours
							extra_hours = t.getHours_worked() - 8;
	
							total += (c.getHourly_rate() * 8)+ (1.5 * c.getHourly_rate() * extra_hours);
						} 
						else {
							total += (t.getHours_worked() * c.getHourly_rate());
						}
						break;

				}
			}
		}
		return total;
	}
	
	
}
