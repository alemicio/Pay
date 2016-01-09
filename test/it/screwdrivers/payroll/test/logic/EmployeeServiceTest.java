package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.*;

import javax.inject.Inject;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.logic.EmployeeService;
import it.screwdrivers.payroll.model.employee.Employee;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.test.ArquillianTest;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class EmployeeServiceTest extends ArquillianTest {
	
	@Inject
	EmployeeDao employee_dao;
	
	@Inject
	EmployeeService employee_service;

	@Test
	public void testGetEmployeeByUsernameAndPassword() {

		SalariedEmployee employee1 = new SalariedEmployee();
		employee1.setName("davide");
		employee1.setSurname("bonamico");
		employee1.setUsername("bonaz");
		employee1.setPassword("bingo");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setMonthly_salary(1000);
		employee_dao.add(employee1);
		
		Employee employee2 = employee_service.getEmployeeByUsernameAndPassword("bonaz", "bingo");
		assertEquals(employee1.getId(), employee2.getId());
		employee_dao.remove(employee1);
	}
}
