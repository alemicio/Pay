package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.engine.PayrollCalendar;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;
import it.screwdrivers.payroll.pojo.historical.HistoricalSalary;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
		h.setCommission(false);
		
		h_dao.update(h);
		
		// TODO send a email to notify payment
	}
	
	public void registerPay(SalariedEmployee e,float total_charges){

		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(e.getMonthly_salary()-total_charges);
		h.setCommission(false);
		
		h_dao.update(h);
		
		// TODO send a email to notify payment
	}
	
	public void registerPay(ContractorEmployee e, float amount){
		
		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(amount);
		h.setCommission(false);
		
		h_dao.update(h);
		
		// TODO send a email to notify payment
	}
	
	public void registerPay(ContractorEmployee e, float amount, float total_charges){
		
		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(amount-total_charges);
		h.setCommission(false);
		
		h_dao.update(h);
		
		// TODO send a email to notify payment
	}
	
	public void registerPay(CommissionedEmployee e, float amount){
		
		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(amount);
		h.setCommission(true);
		
		h_dao.update(h);
	}

	public void registerPay(EmployeeManager e) {
		
		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(e.getAnnual_rate());
		h.setCommission(false);
		
		h_dao.update(h);
	}
	
	public void registerPay(EmployeeManager e, float total_charges) {
		
		Date date = p_calendar.getToday();
		
		h.setEmployee(e);
		h.setDate(date);
		h.setAmount(e.getAnnual_rate()-total_charges);
		h.setCommission(false);
		
		h_dao.update(h);
	}
	
	public List<HistoricalSalary> getHistoricalSalariesByEmployeeId(int employee_id) {
		
		List<HistoricalSalary> historical_salaries = h_dao.findAll();
		List<HistoricalSalary> filtered_historical_salaries = new ArrayList<HistoricalSalary>();
		
		for(HistoricalSalary hs : historical_salaries) {
			if(hs.getEmployee().getId() == employee_id) {
				
				filtered_historical_salaries.add(hs);
			}
		}
		
		return filtered_historical_salaries;
	}

}
