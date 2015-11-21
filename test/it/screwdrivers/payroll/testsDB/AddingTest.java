package it.screwdrivers.payroll.testsDB;

import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AddingTest extends ArquillianTest {
	
	@Inject EmployeeDao employee_dao;

	@Test
	public void test() {
		SalariedEmployee employee_salaried=new SalariedEmployee();
		employee_salaried.setName("davide");
		employee_salaried.setSurname("bonamico");
		employee_salaried.setUsername("bonaz");
		employee_salaried.setPassword("bingo");
		employee_salaried.setE_mail("a@bi.it");
		employee_salaried.setPhone_number("3331112233");
		employee_salaried.setPostal_address("via roma 1");
		employee_salaried.setMonthly_salary(1000);
		
		Boolean test=false;
		employee_dao.add(employee_salaried);
		test=true;
		assertTrue(test);

	}

}
