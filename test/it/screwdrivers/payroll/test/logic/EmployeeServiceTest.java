package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.logic.EmployeeService;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
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

		// Create a new employee
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

		// Search the employee with username and password equal to those of
		// employee1
		Employee employee2 = employee_service.getEmployeeByUsernameAndPassword(
				"bonaz", "bingo");

		// Assert if the id of the retrieved employee2 corresponds to the id of
		// employee1
		assertEquals(employee1.getId(), employee2.getId());

		// Since the test is finished, we can remove employee1, which was added
		// to the db
		employee_dao.remove(employee1);
	}

	@Test
	public void testChangeEmployeeDetails() {

		// Create a SalariedEmployee
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

		Employee employee2 = null;

		// Change the password
		employee1.setPassword("new_password");

		// Update employee1
		employee_dao.update(employee1);

		List<Employee> employees = employee_dao.findAll();
		for (Employee e : employees) {

			if (e.getId() == employee1.getId()) {
				employee2 = e;
				break;
			}
		}

		assertEquals(employee1.getId(), employee2.getId());
		employee_dao.remove(employee1);
	}

	@Test
	public void testUpdateSalariedEmployeeMonthlySalary() {

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

		SalariedEmployee employee2 = null;

		int employee_id = employee1.getId();
		employee_service.updateSalariedEmployeeMonthlySalary(employee_id, 1999);

		List<SalariedEmployee> employees = employee_dao.findAllSalaried();
		for (SalariedEmployee se : employees) {

			if (se.getId() == employee_id) {
				employee2 = se;
				break;
			}
		}

		assertTrue(employee2.getMonthly_salary() == 1999);
		employee_dao.remove(employee1);
	}

	@Test
	public void testUpdateCommissionedEmployeeMonthlySalarySaleRate() {

		CommissionedEmployee employee1 = new CommissionedEmployee();
		employee1.setName("gianpaolo");
		employee1.setSurname("molinelli");
		employee1.setUsername("moli");
		employee1.setPassword("moli");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setMonthly_salary(2000);
		employee1.setSale_rate(2);
		employee_dao.add(employee1);

		CommissionedEmployee employee2 = null;

		int employee_id = employee1.getId();
		employee_service.updateCommissionedEmployeeMonthlySalarySaleRate(
				employee_id, 1999, 50);

		List<CommissionedEmployee> employees = employee_dao
				.findAllCommissioned();
		for (CommissionedEmployee ce : employees) {

			if (ce.getId() == employee_id) {
				employee2 = ce;
				break;
			}
		}

		assertTrue(employee2.getMonthly_salary() == 1999);
		assertTrue(employee2.getSale_rate() == 50);
		employee_dao.remove(employee1);
	}

	@Test
	public void testUpdateContractorEmployeeHourlyRate() {

		ContractorEmployee employee1 = new ContractorEmployee();
		employee1.setName("andrea");
		employee1.setSurname("mognaschi");
		employee1.setUsername("munci");
		employee1.setPassword("munci");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setHourly_rate(10);
		employee_dao.add(employee1);

		ContractorEmployee employee2 = null;

		int employee_id = employee1.getId();
		employee_service.updateContractorEmployeeHourlyRate(employee_id, 10);

		List<ContractorEmployee> employees = employee_dao.findAllContractor();
		for (ContractorEmployee ce : employees) {

			if (ce.getId() == employee_id) {
				employee2 = ce;
				break;
			}
		}

		assertTrue(employee2.getHourly_rate() == 10);
		employee_dao.remove(employee1);
	}

	@Test
	public void testDeleteEmployee() {

		boolean was_deleted = true;

		ContractorEmployee employee1 = new ContractorEmployee();
		employee1.setName("andrea");
		employee1.setSurname("mognaschi");
		employee1.setUsername("munci");
		employee1.setPassword("munci");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setHourly_rate(10);
		employee_dao.add(employee1);

		int employee_id = employee1.getId();
		employee_service.deleteEmployee(employee_id);

		List<Employee> employees = employee_dao.findAll();
		for (Employee e : employees) {

			if (e.getId() == employee_id)
				was_deleted = false;
		}

		assertTrue(was_deleted);
		employee_dao.remove(employee1);
	}

	@Test
	public void testAddEmployee() {
		
		boolean was_added = false;

		ContractorEmployee employee1 = new ContractorEmployee();
		employee1.setName("andrea");
		employee1.setSurname("mognaschi");
		employee1.setUsername("munci");
		employee1.setPassword("munci");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setHourly_rate(10);
		
		employee_service.addEmployee(employee1);
		
		List<Employee> employees = employee_dao.findAll();
		for(Employee e : employees) {
			
			if(e.getId() == employee1.getId())
				was_added = true;
		}
		
		assertTrue(was_added);
		employee_dao.remove(employee1);
	}
	
	@Test
	public void testEmployeeIsNotAddedIfUsernameIsNotAvailable(){
		
		// hp: the employee table must be empty
		
		boolean was_not_added = true;
		
		ContractorEmployee employee1 = new ContractorEmployee();
		employee1.setName("andrea");
		employee1.setSurname("mognaschi");
		employee1.setUsername("munci");
		employee1.setPassword("munci");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setHourly_rate(10);
		
		employee_service.addEmployee(employee1);
		
		ContractorEmployee employee2 = new ContractorEmployee();
		employee2.setName("andrew");
		employee2.setSurname("mognussen");
		employee2.setUsername("munci");
		employee2.setPassword("munci");
		employee2.setE_mail("a@bi.it");
		employee2.setPhone_number("3331112233");
		employee2.setPostal_address("via roma 1");
		employee2.setHourly_rate(10);
		
		employee_service.addEmployee(employee2);
		
		// It will test if employee2 was not added
		List<Employee> employees = employee_dao.findAll();
		for(Employee e : employees) {
			
			if(e.getId() == employee2.getId())
				was_not_added = false;
		}
		
		assertTrue(was_not_added);
		
		employee_dao.remove(employee1);
		if(!was_not_added){
			employee_dao.remove(employee2);
		}	
	}
}
