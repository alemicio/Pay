package it.screwdrivers.payroll.dao_tests;

import static org.junit.Assert.*;

import java.util.List;

import it.screwdrivers.payroll.ArquillianTest;
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
public class EmployeeDaoTest extends ArquillianTest {

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
				// Once you have verified that the employee was written is
				// deleted from the db
				employee_dao.remove(employee);
			}
		}

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
				// Once you have verified that the employee was written is
				// deleted from the db
				employee_dao.remove(employee);
			}
		}

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
				// Once you have verified that the employee was written is
				// deleted from the db
				employee_dao.remove(employee);
			}
		}

		assertTrue(test);
	}

	@Test
	public void testAddingEmployeeManager() {

		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);

		Boolean test = false;
		employee_dao.add(employee_manager);

		List<Employee> employees = employee_dao.findAll();

		for (Employee employee : employees) {
			if (employee.getUsername().equals("danny")) {

				test = true;
				// Once you have verified that the employee was written is
				// deleted from the db
				employee_dao.remove(employee);
			}
		}

		assertTrue(test);
	}

	// This methods tests Employee record update. In particular,
	// it tests if an EmployeeManager's name was correctly updated,
	// after it was persisted in the database
	@Test
	public void testEmployeeUpdate() {

		boolean was_updated = false;

		// EmployeeManager object instantiation
		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);

		// Here it persists the EmployeeManager object in the db
		employee_dao.add(employee_manager);

		// Iterates over employees list retrieved by findAll method
		// to find an employee with username "danny"; than, it changes
		// EmployeeManager's name from "daniele" to "alberto" and calls
		// update method to persist this change in the db
		List<Employee> employees = employee_dao.findAll();
		for (Employee employee : employees) {
			if (employee.getUsername().equals("danny")) {

				employee.setName("alberto");
				employee_dao.update(employee);
			}
		}

		// Iterates over the employees list to find an
		// EmployeeManager with username "danny" and
		// name "alberto". If it founds thi EmployeeManager,
		// the was_updated variable is set to true and the
		// employee removed from the db
		employees = employee_dao.findAll();
		for (Employee employee : employees) {

			if (employee.getUsername().equals("danny")) {
				if (employee.getName().equals("alberto")) {

					was_updated = true;
					employee_dao.remove(employee);
				}
			}
		}

		assertTrue(was_updated);
	}

	// This method tests if the findAll method retrieves a List
	// of EmployeeManager objects which corresponds to all 
	// the employee table's records.
	// We make an initial hypothesis: there are no records in
	// employee table
	@Test
	public void testFindAllEmployees() {
		
		boolean test = false;
		
		// Here it instantiates three employees
		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);
		
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
		
		ContractorEmployee employee_contractor = new ContractorEmployee();
		employee_contractor.setName("andrea");
		employee_contractor.setSurname("mognaschi");
		employee_contractor.setUsername("munci");
		employee_contractor.setPassword("munci");
		employee_contractor.setE_mail("a@bi.it");
		employee_contractor.setPhone_number("3331112233");
		employee_contractor.setPostal_address("via roma 1");
		employee_contractor.setHourly_rate(10);
		
		// The three employees are persisted in the db
		employee_dao.add(employee_manager);
		employee_dao.add(employee_commissioned);
		employee_dao.add(employee_contractor);
		
		// Now, since they were persisted, the number of
		// employee records in the employee table should
		// be three
		List<Employee> employees = employee_dao.findAll();
		
		if(employees.size() == 3){
			
			test = true;
			
			employee_dao.remove(employee_manager);
			employee_dao.remove(employee_commissioned);
			employee_dao.remove(employee_contractor);
		}
		
		assertTrue(test);
	}
}
