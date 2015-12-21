package it.screwdrivers.payroll.testENGINE;

import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.engine.PayEngine;
import it.screwdrivers.payroll.engine.PayEngineFactory;
import it.screwdrivers.payroll.engine.PayrollCalendar;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
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
		
		// pay of salaried employee
		PayEngine pay_engine = pay_engine_factory.getPayEngine("SalariedEmployee");

		pay_engine.pay();

		// check if payment is made
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
		
		//check if it has written pay record for each salaried employee
		// note that the test just works fine one time per day.
		boolean condition = salaried_employees.size() == count;
		
		
		assertTrue("Test if i add on the db rows for each salariedpay",condition);
	}
	
	@Test
	public void PaymentContractor() {
		
		// starting with the remove of all instances of historical salary in the db
		List<HistoricalSalary> hs = hs_dao.findAll();
		
		int count= 0;

				
		// pagamento dei contractor
		PayEngine pay_engine = pay_engine_factory.getPayEngine("ContractorEmployee");
		
		pay_engine.pay();
		
		// check if payment is made
		for (HistoricalSalary h : hs) {

			if (h.getEmployee().getClass().getSimpleName().equals("ContractorEmployee") ) {
						
				//check if i added for today the payment record
				if(h.getDate().getDate() == p_calendar.getToday().getDate() && 
				   h.getDate().getMonth() == p_calendar.getToday().getMonth() &&
				   h.getDate().getYear() == p_calendar.getToday().getYear()){
							
							count++;
						}
						
					}
				}
				
				List<ContractorEmployee> contractor_employees = e_dao.findAllContractor();
				
				//check if it has written pay record for each salaried employee
				// note that the test just works fine one time per day.
				boolean condition = contractor_employees.size() == count;
				
				
				assertTrue("Test if i add on the db rows for each contractorpay",condition);
		
		

	}
	

}
