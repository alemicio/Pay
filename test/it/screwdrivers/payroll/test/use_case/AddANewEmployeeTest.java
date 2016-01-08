package it.screwdrivers.payroll.test.use_case;

import static org.junit.Assert.assertEquals;
import it.screwdrivers.payroll.controller.EmployeeController;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.test.ArquillianTest;
import it.screwdrivers.payroll.test.EmployeeGenerator;

import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OverProtocol;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AddANewEmployeeTest {

	@Inject
	EmployeeController employee_controller;

	@Deployment(name = "Test")
	@OverProtocol("Servlet 3.0")
	public static Archive<?> createDeployment() {

		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "test_archive.war")
				.addClass(ArquillianTest.class)
				.addClass(EmployeeGenerator.class)
				.addPackages(true, "it.screwdrivers.payroll.view")
				.addPackages(true, "it.screwdrivers.payroll.controller")
				.addPackages(true, "it.screwdrivers.payroll.dao")
				.addPackages(true, "it.screwdrivers.payroll.engine")
				.addPackages(true, "it.screwdrivers.payroll.model.employee")
				.addPackages(true, "it.screwdrivers.payroll.model.card")
				.addPackages(true, "it.screwdrivers.payroll.model.historical")
				.addPackages(true, "it.screwdrivers.payroll.model.payment")
				.addPackages(true, "it.screwdrivers.payroll.model.union")
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"));

		return archive;
	}

	@Test
	public void testDoesNotAddEmployeeIfUsernameIsNotAvailable() {

		List<SalariedEmployee> salaried_employees = employee_controller
				.getAllSalaried();
		List<CommissionedEmployee> commissioned_employees = employee_controller
				.getAllCommissioned();
		List<ContractorEmployee> contractor_employees = employee_controller
				.getAllContractors();

		if (salaried_employees.size() > 0) {
			int initial_size = salaried_employees.size();
			SalariedEmployee employee = salaried_employees.get(0);
			employee_controller.addEmployee(employee);
			int final_size = employee_controller.getAllSalaried().size();
			assertEquals(initial_size, final_size);
			if (final_size > initial_size)
				employee_controller.deleteEmployee(employee.getId());

		} else if (commissioned_employees.size() > 0) {
			int initial_size = commissioned_employees.size();
			CommissionedEmployee employee = commissioned_employees.get(0);
			employee_controller.addEmployee(employee);
			int final_size = employee_controller.getAllCommissioned().size();
			assertEquals(initial_size, final_size);
			if (final_size > initial_size)
				employee_controller.deleteEmployee(employee.getId());

		} else if (contractor_employees.size() > 0) {
			int initial_size = contractor_employees.size();
			ContractorEmployee employee = contractor_employees.get(0);
			employee_controller.addEmployee(employee);
			int final_size = employee_controller.getAllContractors().size();
			assertEquals(initial_size, final_size);
			if (final_size > initial_size)
				employee_controller.deleteEmployee(employee.getId());

		} else {
			SalariedEmployee employee = new SalariedEmployee();
			employee.setName("davide");
			employee.setSurname("bonamico");
			employee.setUsername("bonaz");
			employee.setPassword("bingo");
			employee.setE_mail("a@bi.it");
			employee.setPhone_number("3331112233");
			employee.setPostal_address("via roma 1");
			employee.setMonthly_salary(1000);

			SalariedEmployee employee2 = new SalariedEmployee();
			employee2.setName("davide");
			employee2.setSurname("bonamico");
			employee2.setUsername("bonaz");
			employee2.setPassword("bingo");
			employee2.setE_mail("a@bi.it");
			employee2.setPhone_number("3331112233");
			employee2.setPostal_address("via roma 1");
			employee2.setMonthly_salary(1000);

			employee_controller.addEmployee(employee);
			employee_controller.addEmployee(employee2);

			assertEquals(employee_controller.getAllSalaried().size(), 1);

			employee_controller.deleteEmployee(employee.getId());
			if (employee_controller.getAllSalaried().size() == 1)
				employee_controller.deleteEmployee(employee2.getId());
		}
	}
	
	@Test
	public void testAddEmployeeIfUsernameIsAvailable(){
		
		// Initial hypothesis: empty Employee table
		
		SalariedEmployee employee = new SalariedEmployee();
		employee.setName("davide");
		employee.setSurname("bonamico");
		employee.setUsername("bonaz");
		employee.setPassword("bingo");
		employee.setE_mail("a@bi.it");
		employee.setPhone_number("3331112233");
		employee.setPostal_address("via roma 1");
		employee.setMonthly_salary(1000);
		
		employee_controller.addEmployee(employee);
		List<SalariedEmployee> salaried_employees = employee_controller.getAllSalaried();
		
		assertEquals(salaried_employees.size(), 1);
	}
}
