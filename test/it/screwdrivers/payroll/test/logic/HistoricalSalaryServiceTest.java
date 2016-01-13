package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.model.historical.HistoricalSalary;
import it.screwdrivers.payroll.test.ArquillianTest;
import it.screwdrivers.payroll.test.DbSeeder;

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
public class HistoricalSalaryServiceTest {

	@Inject
	HistoricalSalaryService hs_service;

	@Inject
	HistoricalSalaryDao hs_dao;

	@Inject
	EmployeeDao employee_dao;

	@Deployment(name = "Test")
	@OverProtocol("Servlet 3.0")
	public static Archive<?> createDeployment() {

		WebArchive archive = ShrinkWrap
				.create(WebArchive.class, "test_archive.war")
				.addClass(ArquillianTest.class)
				.addClass(DbSeeder.class)
				.addPackages(true, "it.screwdrivers.payroll.controller")
				.addPackages(true, "it.screwdrivers.payroll.logic")
				.addPackages(true, "it.screwdrivers.payroll.dao")
				.addPackages(true, "it.screwdrivers.payroll.engine")
				.addPackages(true, "it.screwdrivers.payroll.model.card")
				.addPackages(true, "it.screwdrivers.payroll.model.employee")
				.addPackages(true, "it.screwdrivers.payroll.model.historical")
				.addPackages(true, "it.screwdrivers.payroll.model.payment")
				.addPackages(true, "it.screwdrivers.payroll.model.union")
				.addPackages(true, "it.screwdrivers.payroll.test.engine")
				.addPackages(true, "it.screwdrivers.payroll.test.dao")
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE,
						ArchivePaths.create("beans.xml"));

		// archive.as(ZipExporter.class).exportTo(
		// new File("target/test_archive.war"), true);

		return archive;
	}

	@Test
	public void testRegisterPaySalariedEmployee() {

		boolean was_registered = false;

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

		hs_service.registerPay((SalariedEmployee) employee1);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary hs : historical_salaries) {

			if (hs.getEmployee().getId() == employee1.getId()
					&& hs.getAmount() == 1000) {

				was_registered = true;
				hs_dao.remove(hs);
			}
		}

		assertTrue(was_registered);
		employee_dao.remove(employee1);
	}

	@Test
	public void testRegisterPayContractorEmployeeAmount() {

		boolean was_registered = false;

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

		hs_service.registerPay(employee1, 50);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary hs : historical_salaries) {

			if (hs.getEmployee().getId() == employee1.getId()
					&& hs.getAmount() == 50) {

				was_registered = true;
				hs_dao.remove(hs);
			}
		}

		assertTrue(was_registered);
		employee_dao.remove(employee1);
	}

	@Test
	public void testRegisterPayContractorEmployeeAmountAndTotalCharges() {

		boolean was_registered = false;
		boolean amount_correctly_computed = false;

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

		hs_service.registerPay(employee1, 50, 20);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary hs : historical_salaries) {

			if (hs.getEmployee().getId() == employee1.getId()) {

				was_registered = true;
				if (hs.getAmount() == 50 - 20)
					amount_correctly_computed = true;
				hs_dao.remove(hs);
			}
		}

		assertTrue(was_registered);
		assertTrue(amount_correctly_computed);
		employee_dao.remove(employee1);
	}

	@Test
	public void testRegisterPayCommissioned() {

		boolean was_registered = false;

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

		hs_service.registerPay(employee1, 500);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary hs : historical_salaries) {

			if (hs.getEmployee().getId() == employee1.getId()
					&& hs.getAmount() == 500) {
				was_registered = true;
				hs_dao.remove(hs);
			}
		}

		assertTrue(was_registered);
		employee_dao.remove(employee1);
	}

	@Test
	public void testRegisterPayManager() {

		boolean was_registered = false;
		boolean amount_correctly_computed = false;

		EmployeeManager employee1 = new EmployeeManager();
		employee1.setName("daniele");
		employee1.setSurname("montagna");
		employee1.setUsername("danny");
		employee1.setPassword("danny");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setAnnual_rate(12000);
		employee_dao.add(employee1);

		hs_service.registerPay(employee1);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary hs : historical_salaries) {

			if (hs.getEmployee().getId() == employee1.getId()) {
				was_registered = true;
				if (hs.getAmount() == employee1.getAnnual_rate())
					amount_correctly_computed = true;
				hs_dao.remove(hs);
			}
		}

		assertTrue(was_registered);
		assertTrue(amount_correctly_computed);
		employee_dao.remove(employee1);
	}

	@Test
	public void registerPayManagerTotalCharges() {

		boolean was_registered = false;
		boolean amount_correctly_computed = false;

		EmployeeManager employee1 = new EmployeeManager();
		employee1.setName("daniele");
		employee1.setSurname("montagna");
		employee1.setUsername("danny");
		employee1.setPassword("danny");
		employee1.setE_mail("a@bi.it");
		employee1.setPhone_number("3331112233");
		employee1.setPostal_address("via roma 1");
		employee1.setAnnual_rate(12000);
		employee_dao.add(employee1);

		hs_service.registerPay(employee1, 500);

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary hs : historical_salaries) {

			if (hs.getEmployee().getId() == employee1.getId()) {
				was_registered = true;
				if (hs.getAmount() == employee1.getAnnual_rate() - 500)
					amount_correctly_computed = true;
				hs_dao.remove(hs);
			}
		}

		assertTrue(was_registered);
		assertTrue(amount_correctly_computed);
		employee_dao.remove(employee1);
	}
}
