package it.screwdrivers.payroll.testsDB;

import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AddingTest extends ArquillianTest {

	@Inject
	EmployeeDao employee_dao;

	@Test
	public void testAddingSalaried() {
		SalariedEmployee employee_salaried = new SalariedEmployee();
		employee_salaried.setName("davide");
		employee_salaried.setSurname("bonamico");
		employee_salaried.setUsername("bonaz");
		employee_salaried.setPassword("bingo");
		employee_salaried.setE_mail("a@bi.it");
		employee_salaried.setPhone_number("3331112233");
		employee_salaried.setPostal_address("via roma 1");
		employee_salaried.setMonthly_salary(1000);

		Boolean test = false;
		employee_dao.add(employee_salaried);

		List<Employee> employees = employee_dao.findAll();

		for (Employee employee : employees) {

			if (employee.getUsername().equals("bonaz")) {
				test = true;
			}
		}

		// test = true;
		assertTrue(test);
	}

	@Test
	public void testAddingContractor() {
		ContractorEmployee employee_contractor = new ContractorEmployee();
		employee_contractor.setName("andrea");
		employee_contractor.setSurname("mognaschi");
		employee_contractor.setUsername("munci");
		employee_contractor.setPassword("munci");
		employee_contractor.setE_mail("a@bi.it");
		employee_contractor.setPhone_number("3331112233");
		employee_contractor.setPostal_address("via roma 1");

		employee_contractor.setHourly_rate(10);

		Boolean test = false;
		employee_dao.add(employee_contractor);

		List<Employee> employees = employee_dao.findAll();

		for (Employee employee : employees) {

			if (employee.getUsername().equals("munci")) {
				test = true;
			}
		}
		// test = true;
		assertTrue(test);
	}

	@Test
	public void testAddingCommissioned() {
		CommissionedEmployee employee_commissioned = new CommissionedEmployee();
		employee_commissioned.setName("gianpaolo");
		employee_commissioned.setSurname("molinelli");
		employee_commissioned.setUsername("moli");
		employee_commissioned.setPassword("moli");
		employee_commissioned.setE_mail("a@bi.it");
		employee_commissioned.setPhone_number("3331112233");
		employee_commissioned.setPostal_address("via roma 1");

		employee_commissioned.setMonthly_salary(2000);
		employee_commissioned.setSale_rate(2);

		Boolean test = false;
		employee_dao.add(employee_commissioned);

		List<Employee> employees = employee_dao.findAll();

		for (Employee employee : employees) {

			if (employee.getUsername().equals("moli")) {
				test = true;
			}
		}
		test = true;
		assertTrue(test);
	}
}
