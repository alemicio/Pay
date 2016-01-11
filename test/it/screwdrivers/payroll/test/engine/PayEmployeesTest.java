package it.screwdrivers.payroll.test.engine;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.engine.PayEngine;
import it.screwdrivers.payroll.engine.PayEngineFactory;
import it.screwdrivers.payroll.logic.CalendarService;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.model.historical.HistoricalSalary;
import it.screwdrivers.payroll.test.ArquillianTest;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class PayEmployeesTest extends ArquillianTest {

	@Inject
	EmployeeDao e_dao;
	
	@Inject
	HistoricalSalaryDao hs_dao;
	
	@Inject
	PayEngineFactory pay_engine_factory;	
	
	@Inject
	CalendarService calendar_service;

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
			if (h.getEmployee().getClass().getSimpleName()
					.equals("EmployeeManager")) {
				before++;
			}
		}

		// Perform employee managers payment
		PayEngine pay_engine = pay_engine_factory
				.getPayEngine("EmployeeManager");
		pay_engine.pay();

		// Find all historical salaries after the payment
		hs = hs_dao.findAll();

		// Count the number of employee manager historical salaries after pay
		for (HistoricalSalary h : hs) {
			if (h.getEmployee().getClass().getSimpleName()
					.equals("EmployeeManager")) {
				after++;
			}
		}

		boolean condition = (after - before) == e_dao.findAllManager().size();

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary historical_salary : historical_salaries) {

			hs_dao.remove(historical_salary);
		}

		// Assert if the number of historical salaries just created is equal
		// to the number of employee managers. If the condition is true, it
		// means
		// that all the employee managers were successfully paid.
		assertTrue("All employee managers were successfully paid.", condition);
	}

	//@Test
	public void paymentSalaried() {

		// starting with the remove of all instances of historical salary in the
		// db
		List<HistoricalSalary> hs = hs_dao.findAll();

		int count = 0;

		// pay of salaried employee
		PayEngine pay_engine = pay_engine_factory
				.getPayEngine("SalariedEmployee");

		pay_engine.pay();

		// check if payment is made
		for (HistoricalSalary h : hs) {
			if (h.getEmployee().getClass().getSimpleName()
					.equals("SalariedEmployee")
					|| h.getEmployee().getClass().getSimpleName()
							.equals("CommissionedEmployee")) {

				// check if i added for today the payment record
				if (h.getDate().getDate() == calendar_service.getToday().getDate()
						&& h.getDate().getMonth() == calendar_service.getToday()
								.getMonth()
						&& h.getDate().getYear() == calendar_service.getToday()
								.getYear()) {

					count++;
				}

			}
		}

		List<SalariedEmployee> salaried_employees = e_dao.findAllSalaried();

		// check if it has written pay record for each salaried employee
		// note that the test just works fine one time per day.
		boolean condition = salaried_employees.size() == count;

		for (SalariedEmployee se : salaried_employees) {
			e_dao.remove(se);
		}

		assertTrue("Test if i add on the db rows for each salariedpay",
				condition);
	}

	@SuppressWarnings("deprecation")
	//@Test
	public void paymentContractor() {

		List<HistoricalSalary> hs = hs_dao.findAll();

		int count = 0;

		// pay action for contractors
		PayEngine pay_engine = pay_engine_factory
				.getPayEngine("ContractorEmployee");

		pay_engine.pay();

		// check if payment is made
		for (HistoricalSalary h : hs) {

			if (h.getEmployee().getClass().getSimpleName()
					.equals("ContractorEmployee")) {

				// check if i added for today the payment record
				if (h.getDate().getDate() == calendar_service.getToday().getDate()
						&& h.getDate().getMonth() == calendar_service.getToday()
								.getMonth()
						&& h.getDate().getYear() == calendar_service.getToday()
								.getYear()) {

					count++;
				}

			}
		}

		List<ContractorEmployee> contractor_employees = e_dao
				.findAllContractor();

		// check if it has written pay record for each salaried employee
		// note that the test just works fine one time per day.
		boolean condition = contractor_employees.size() == count;

		assertTrue("Test if i add on the db rows for each contractorpay",
				condition);

	}

	@SuppressWarnings("deprecation")
	//@Test
	public void paymentCommissioned() {

		List<HistoricalSalary> hs = hs_dao.findAll();

		int count = 0;

		// pay action on commission for commissioned emps.
		PayEngine pay_engine = pay_engine_factory
				.getPayEngine("CommissionedEmployee");

		System.out.println(pay_engine.toString());
		pay_engine.pay();

		// check if payment is made
		// for (HistoricalSalary h : hs) {
		//
		// if
		// (h.getEmployee().getClass().getSimpleName().equals("CommissionedEmployee"))
		// {
		//
		// // check if i added for today the payment record
		// if (h.getDate().getDate() == calendar_service.getToday().getDate() &&
		// h.getDate().getMonth() == calendar_service.getToday().getMonth() &&
		// h.getDate().getYear() == calendar_service.getToday().getYear()) {
		//
		// count++;
		// }
		//
		// }
		// }
		//
		// List<CommissionedEmployee> commissioned_employees =
		// e_dao.findAllCommissioned();
		//
		//
		// // check if it has written pay record for each salaried employee
		// // note that the test just works fine one time per day.
		// boolean condition = commissioned_employees.size() == count;
		//
		// assertTrue("Test if i add on the db rows for each commissionedpay",condition);

	}

}
