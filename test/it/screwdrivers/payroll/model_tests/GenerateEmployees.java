package it.screwdrivers.payroll.model_tests;


import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.ArquillianTest;
import it.screwdrivers.payroll.controller.EmployeeController;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.Employee;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;

import javax.inject.Inject;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GenerateEmployees extends ArquillianTest {
	
	@Inject
	EmployeeDao employee_dao;
	
	@Inject
	EmployeeController e_controller;

	@Test
	public void testAddingSalariedClones() {
		
		//add a several number of employees in order to populate the db
		for(int i = 0; i < 10 ; i++){
			SalariedEmployee employee_salaried = new SalariedEmployee();
			employee_salaried.setName("clone"+i);
			employee_salaried.setSurname("clone"+i);
			employee_salaried.setUsername("salaried_clone"+i);
			employee_salaried.setPassword("clone"+i);
			employee_salaried.setE_mail("clone@salaried.it");
			employee_salaried.setPhone_number("3331112233");
			employee_salaried.setPostal_address("via roma 1");
			employee_salaried.setMonthly_salary(1000);
			//employee_dao.add(employee_salaried);
			e_controller.addEmployee(employee_salaried);
		}

		int count = 0;
		boolean condition = false;

		List<Employee> employees = employee_dao.findAll();

		for (Employee employee : employees) {

			if (employee.getE_mail().equals("clone@salaried.it")) {
				
				count++;
			}
		}
		
		if(10 == count){
			condition = true;
		}
		
		assertTrue("ho aggiunto tutti e 10 i cloni correttamente al db", condition);
		
	}
	@Test
	public void testAddingContractorClones() {
		
		//add a several number of employees in order to populate the db
		for(int i = 0; i < 10 ; i++){
			ContractorEmployee employee_contractor = new ContractorEmployee();
			employee_contractor.setName("clone"+i);
			employee_contractor.setSurname("clone"+i);
			employee_contractor.setUsername("contractor_clone"+i);
			employee_contractor.setPassword("clone"+i);
			employee_contractor.setE_mail("clone@contractor.it");
			employee_contractor.setPhone_number("3331112233");
			employee_contractor.setPostal_address("via roma 1");
			employee_contractor.setHourly_rate(8);
			employee_dao.add(employee_contractor);
		}

		int count = 0;
		boolean condition = false;

		List<Employee> employees = employee_dao.findAll();

		for (Employee employee : employees) {

			if (employee.getE_mail().equals("clone@contractor.it")) {
				
				count++;
			}
		}
		
		if(10 == count){
			condition = true;
		}
		
		assertTrue("ho aggiunto tutti e 10 i cloni correttamente al db", condition);
		
	}
	@Test
	public void testAddingCommissionedClones() {
		
		//add a several number of employees in order to populate the db
		for(int i = 0; i < 10 ; i++){
			CommissionedEmployee employee_commissioned = new CommissionedEmployee();
			employee_commissioned.setName("clone"+i);
			employee_commissioned.setSurname("clone"+i);
			employee_commissioned.setUsername("commissioned_clone"+i);
			employee_commissioned.setPassword("clone"+i);
			employee_commissioned.setE_mail("clone@commissioned.it");
			employee_commissioned.setPhone_number("3331112233");
			employee_commissioned.setPostal_address("via roma 1");
			employee_commissioned.setMonthly_salary(1200);
			employee_commissioned.setSale_rate((float) 0.1);
			employee_dao.add(employee_commissioned);
		}

		int count = 0;
		boolean condition = false;

		List<Employee> employees = employee_dao.findAll();

		for (Employee employee : employees) {

			if (employee.getE_mail().equals("clone@commissioned.it")) {
				
				count++;
			}
		}
		
		if(10 == count){
			condition = true;
		}
		
		assertTrue("ho aggiunto tutti e 10 i cloni correttamente al db", condition);
		
	}
}
