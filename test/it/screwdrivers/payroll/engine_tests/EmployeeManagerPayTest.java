package it.screwdrivers.payroll.engine_tests;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.ArquillianTest;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.engine.PayEngine;
import it.screwdrivers.payroll.engine.PayEngineFactory;
import it.screwdrivers.payroll.engine.PayrollCalendar;
import it.screwdrivers.payroll.model.historical.HistoricalSalary;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EmployeeManagerPayTest extends ArquillianTest {

	@Inject
	EmployeeDao e_dao;
	@Inject
	PayEngineFactory pay_engine_factory;
	@Inject
	HistoricalSalaryDao hs_dao;
	@Inject
	PayrollCalendar p_calendar;

	@Test
	public void testIfManagerEmployeesArePaid() {
		
		// Number of employee managers historical salaries before pay
		int before = 0;
		
		// Number of employee managers historical salaries after pay
		int after = 0;
		
		// Get the list of all historical salaries
		List<HistoricalSalary> hs = hs_dao.findAll();
		
		// Count the number of employee manager historical salaries
		for (HistoricalSalary h : hs) {
			if (h.getEmployee().getClass().getSimpleName().equals("EmployeeManager")) {
				
				before++;
			}
		}

		// Perform employee managers payment
		PayEngine pay_engine = pay_engine_factory.getPayEngine("EmployeeManager");
		pay_engine.pay();
		
		// Find all historical salaries after the payment
		hs = hs_dao.findAll();

		// Count the number of employee manager historical salaries after pay
		for (HistoricalSalary h : hs) {
			if (h.getEmployee().getClass().getSimpleName().equals("EmployeeManager")) {

				after++;
			}
		}
		
		System.out.println("Number of employeemanagers historical salaries before payment: " + before);
		System.out.println("Number of employeemanagers historical salaries after payment: " + after);
		
		boolean condition = (after - before) == e_dao.findAllManager().size();
		
		// Assert if the number of historical salaries just created is equal
		// to the number of employee managers. If the condition is true, it means
		// that all the employee managers were successfully paid.
		assertTrue("All employee managers were successfully paid.", condition);
	}
}

