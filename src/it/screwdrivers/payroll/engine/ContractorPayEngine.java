package it.screwdrivers.payroll.engine;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.controller.TimeCardController;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.card.TimeCard;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;


import it.screwdrivers.payroll.pojo.employee.Employee;

import javax.inject.Inject;

public class ContractorPayEngine implements IPayEngine {

	@Inject
	EmployeeDao e_dao;
	@Inject
	TimeCardController t_controller;
	@Inject
	HistoricalSalarycontroller h_controller;
	@Inject
	PayrollCalendar p_calendar;

	@Override
	public void pay() {

		List<ContractorEmployee> c_employees = e_dao.findAllContractor();
		List<TimeCard> retrieved;
		List<Date> working_days = p_calendar.contractorLastWeek();
		float total = 0;
		
		for(ContractorEmployee c: c_employees){
			
			retrieved = t_controller.retriveByEmployee(c);
			
			for(TimeCard t: retrieved){
				
				for (Date wd : working_days) {
					
					if(wd == t.getDate()){
						
						if (t.getHours_worked() >= 8) {
							total+= (t.getHours_worked()*1.5*c.getHourly_rate());
						}
						else {
							total+= (t.getHours_worked()*c.getHourly_rate());
						}
					}
				}
			}
			
			h_controller.registerPay(c, total);
		}
		
	}
}
