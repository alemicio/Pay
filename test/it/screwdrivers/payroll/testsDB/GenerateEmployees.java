package it.screwdrivers.payroll.testsDB;


import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GenerateEmployees extends ArquillianTest {
	
	@Inject
	EmployeeDao employee_dao;

	@Test
	public void testAddingSalariedClones() {
		
		//add a several number of employees in order to populate the db
		for(int i = 0; i < 10 ; i++){
			SalariedEmployee employee_salaried = new SalariedEmployee();
			employee_salaried.setName("clone"+i);
			employee_salaried.setSurname("clone"+i);
			employee_salaried.setUsername("clone"+i);
			employee_salaried.setPassword("clone"+i);
			employee_salaried.setE_mail("clone@clone.it");
			employee_salaried.setPhone_number("3331112233");
			employee_salaried.setPostal_address("via roma 1");
			employee_salaried.setMonthly_salary(1000);
			employee_dao.add(employee_salaried);
		}

		int count = 0;
		boolean condition = false;

		List<Employee> employees = employee_dao.findAll();

		for (Employee employee : employees) {

			if (employee.getE_mail().equals("clone@clone.it")) {
				
				count++;
			}
		}
		
		if(10 == count){
			condition = true;
		}
		
		assertTrue("ho aggiunto tutti e 10 i cloni correttamente al db", condition);
		
	}
}
