package it.screwdrivers.payroll.tests;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.ContractorEmployee;
import it.screwdrivers.payroll.pojo.Employee;
import it.screwdrivers.payroll.pojo.EmployeeManager;
import it.screwdrivers.payroll.pojo.SalariedEmployee;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EmployeeTest extends ArquillianTest {

	@Inject
	EmployeeDao employee_dao;

	Employee employee = new Employee();
	CommissionedEmployee comm_employee = new CommissionedEmployee();
	ContractorEmployee conn_employee = new ContractorEmployee();

	@Inject
	SalariedEmployee sala_employee;

	EmployeeManager mana_employee = new EmployeeManager();

	List<Employee> employees;
	List<CommissionedEmployee> comm_employees;
	List<ContractorEmployee> conn_employees;
	List<SalariedEmployee> sala_employees;
	List<EmployeeManager> mana_employees;

	@Test
	public void testCreationEmployee() {
		employee.setName("carlo");
		employee.setSurname("Bobba");
		employee.setE_mail("carlo.bobba01@ATENEOPV.IT");
		employee.setPhone_number("339-3930243940");
		employee.setPostal_address("Pavia, via Olevano");
		employee.setUsername("carlo91b");
		employee.setPassword("password");

		employee_dao.add(employee);

		// Suppose to go down into the database
		employees = employee_dao.findAll();

		// now we check if the person was in fact added
		boolean found = false;

		int number_employees_before = 0;
		int number_employees_after = 0;

		for (Employee e : employees) {

			number_employees_before++;
			if ("carlo91b".equals(e.getUsername())
					&& "password".equals(e.getPassword())) {

				if ("carlo91b".equals(e.getUsername())
						&& "password".equals(e.getPassword())) {

					found = true;
					break;
				}
			}
		}

		Assert.assertTrue("Bobba was found", found);

	}

	@Test
	public void testCreationSalariedEmployee() {
		// sala_employee = (SalariedEmployee) employee;
		sala_employee.setMonthly_salary(1000);

		employee_dao.add(sala_employee);

		// Suppose to go down into the database
		employees = employee_dao.findAll();

		// now we check if the person was in fact added
		boolean found = false;

		// for (Employee e : employees) {
		// if (e.ge) {
		// found = true;
		// break;
		// }
		// }

		Assert.assertTrue("Bobba was found", found);
	}
}
