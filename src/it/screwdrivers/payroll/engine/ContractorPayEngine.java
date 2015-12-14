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
		float extra_hours = 0;
		
		for(ContractorEmployee c: c_employees){
			
			//this method return a list if timecard associated to the 
			//given employee
			retrieved = t_controller.retriveByEmployee(c);
			
			//check if the timeCard has a date matching with 
			// one of the date of the current week
			for(TimeCard t: retrieved){
				
				for (Date wd : working_days) {
					
					//check on the date field
					if(wd == t.getDate()){
						
						//if it works extra hours
						// he will be paid 1,5 times his hourly rate
						// fro each extra hour
						if (t.getHours_worked() > 8) {
							//get extra hours 
							extra_hours = t.getHours_worked()-8;
							
							total+= (c.getHourly_rate()*8) +(1.5*c.getHourly_rate()*extra_hours) ;
						}
						else {
							total+= (t.getHours_worked()*c.getHourly_rate());
						}
						break;
						
					}
				}
			}
			
			h_controller.registerPay(c, total);
		}
		
	}
}
