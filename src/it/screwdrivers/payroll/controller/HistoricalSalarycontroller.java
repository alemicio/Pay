package it.screwdrivers.payroll.controller;

import java.sql.Date;

import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.engine.PayrollCalendar;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;
import it.screwdrivers.payroll.pojo.historical.HistoricalSalary;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HistoricalSalarycontroller {
	
	@Inject
	HistoricalSalary h;
	@Inject
	HistoricalSalaryDao h_dao;
	@Inject
	PayrollCalendar p_calendar;
	
	
	public void registerPay(SalariedEmployee e){
		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(e.getMonthly_salary());
		
		h_dao.add(h);
	}
	
	public void registerPay(ContractorEmployee e, float amount){
		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(amount);
		
		h_dao.add(h);
	}
	
	public void registerPay(CommissionedEmployee e, float amount){
		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(amount);
		
		h_dao.add(h);
	}

}
