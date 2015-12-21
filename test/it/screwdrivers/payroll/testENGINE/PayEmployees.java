package it.screwdrivers.payroll.testENGINE;

import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.engine.PayEngine;
import it.screwdrivers.payroll.engine.PayEngineFactory;
import it.screwdrivers.payroll.engine.PayrollCalendar;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;
import it.screwdrivers.payroll.pojo.historical.HistoricalSalary;
import it.screwdrivers.payroll.testsDB.ArquillianTest;
import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PayEmployees extends ArquillianTest {

	@Inject
	EmployeeDao e_dao;
	@Inject
	PayEngineFactory pay_engine_factory;
	@Inject
	HistoricalSalaryDao hs_dao;
	@Inject
	PayrollCalendar p_calendar;
	

	@Test
	public void PaymentSalaried() {
		
		// starting with the remove of all instances of historical salary in the db
		List<HistoricalSalary> hs = hs_dao.findAll();

		int count = 0;
		
		// pagamento dei salaried
		PayEngine pay_engine = pay_engine_factory.getPayEngine("SalariedEmployee");

		pay_engine.pay();

		// verifica avvenuto pagamento

		System.out.println(hs.size());
		hs = hs_dao.findAll();
		for (HistoricalSalary h : hs) {

			if (h.getEmployee().getClass().getSimpleName().equals("SalariedEmployee") || h.getEmployee().getClass().getSimpleName().equals("CommissionedEmployee")) {
				
				//check if i added for today the payment record
				if(h.getDate().getDate() == p_calendar.getToday().getDate() && 
				   h.getDate().getMonth() == p_calendar.getToday().getMonth() &&
				   h.getDate().getYear() == p_calendar.getToday().getYear()){
					
					count++;
				}
				
			}
		}
		
		List<SalariedEmployee> salaried_employees = e_dao.findAllSalaried();
		
		//controllo che abbia scritto lo stesso numero di record
		boolean condition = salaried_employees.size() == count;
		
		System.out.println(count);
		System.out.println(salaried_employees.size());
		
		assertTrue("Test if i add on the db rows for each salariedpay",condition);

	}
	
	

}
