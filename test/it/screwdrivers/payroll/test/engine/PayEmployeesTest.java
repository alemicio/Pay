package it.screwdrivers.payroll.test.engine;

import static org.junit.Assert.assertTrue;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalSalaryDao;
import it.screwdrivers.payroll.dao.HistoricalUnionChargeDao;
import it.screwdrivers.payroll.dao.TimeCardDao;
import it.screwdrivers.payroll.dao.UnionDao;
import it.screwdrivers.payroll.dao.UnionServiceAssociationDao;
import it.screwdrivers.payroll.dao.UnionServiceDao;
import it.screwdrivers.payroll.engine.PayEngine;
import it.screwdrivers.payroll.engine.PayEngineFactory;
import it.screwdrivers.payroll.logic.CalendarService;
import it.screwdrivers.payroll.logic.HistoricalUnionChargeService;
import it.screwdrivers.payroll.logic.TimeCardService;
import it.screwdrivers.payroll.logic.UnionService;
import it.screwdrivers.payroll.model.card.TimeCard;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;
import it.screwdrivers.payroll.model.historical.HistoricalSalary;
import it.screwdrivers.payroll.model.historical.HistoricalUnionCharge;
import it.screwdrivers.payroll.model.union.Union;
import it.screwdrivers.payroll.model.union.UnionServiceAssociation;
import it.screwdrivers.payroll.test.ArquillianTest;
import it.screwdrivers.payroll.test.DbSeeder;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
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
public class PayEmployeesTest {

	@Inject
	EmployeeDao e_dao;

	@Inject
	TimeCardDao tc_dao;

	@Inject
	HistoricalSalaryDao hs_dao;

	@Inject
	UnionDao union_dao;

	@Inject
	UnionServiceDao us_dao;

	@Inject
	UnionServiceAssociationDao usa_dao;

	@Inject
	HistoricalUnionChargeDao huc_dao;

	@Inject
	PayEngineFactory pay_engine_factory;

	@Inject
	CalendarService calendar_service;

	@Inject
	TimeCardService timecard_service;

	@Inject
	UnionService u_service;

	@Inject
	HistoricalUnionChargeService huc_service;

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
	public void testIfManagerEmployeesArePaid() {

		// hp: tables must be initially empty

		// Create new employee managers
		EmployeeManager employee_manager = new EmployeeManager();
		employee_manager.setName("daniele");
		employee_manager.setSurname("montagna");
		employee_manager.setUsername("danny");
		employee_manager.setPassword("danny");
		employee_manager.setE_mail("a@bi.it");
		employee_manager.setPhone_number("3331112233");
		employee_manager.setPostal_address("via roma 1");
		employee_manager.setAnnual_rate(12000);
		e_dao.add(employee_manager);

		EmployeeManager employee_manager2 = new EmployeeManager();
		employee_manager2.setName("juan pablo");
		employee_manager2.setSurname("montoya");
		employee_manager2.setUsername("juan");
		employee_manager2.setPassword("password");
		employee_manager2.setE_mail("juan@montoya.it");
		employee_manager2.setPhone_number("3331112233");
		employee_manager2.setPostal_address("via roma 1");
		employee_manager2.setAnnual_rate(14000);
		e_dao.add(employee_manager2);

		// Perform employee managers payment
		PayEngine pay_engine = pay_engine_factory
				.getPayEngine("EmployeeManager");
		pay_engine.pay();

		// Find all historical salaries after the payment
		List<HistoricalSalary> hs = hs_dao.findAll();

		int historical_salary_size = hs.size();

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		for (HistoricalSalary historical_salary : historical_salaries) {

			hs_dao.remove(historical_salary);
		}

		e_dao.remove(employee_manager);
		e_dao.remove(employee_manager2);

		// Assert if the number of historical salaries just created is equal
		// to the number of employee managers. If the condition is true, it
		// means that all the employee managers were successfully paid.
		assertTrue(historical_salary_size == 2);
	}

	@Test
	public void testIfSalariedEmployeesArePaid() {

		SalariedEmployee employee_salaried = new SalariedEmployee();
		employee_salaried.setName("harald");
		employee_salaried.setSurname("frentzen");
		employee_salaried.setUsername("heinz");
		employee_salaried.setPassword("password");
		employee_salaried.setE_mail("a@bi.it");
		employee_salaried.setPhone_number("3331112233");
		employee_salaried.setPostal_address("via roma 1");
		employee_salaried.setMonthly_salary(1500);
		e_dao.add(employee_salaried);

		// pay of salaried employee
		PayEngine pay_engine = pay_engine_factory
				.getPayEngine("SalariedEmployee");
		pay_engine.pay();

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		int historical_salaries_size = historical_salaries.size();

		for (HistoricalSalary hs : historical_salaries) {
			hs_dao.remove(hs);
		}
		e_dao.remove(employee_salaried);

		assertTrue(historical_salaries_size == 1);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testIfContractorEmployeesArePaid() {

		ContractorEmployee employee_contractor = new ContractorEmployee();
		employee_contractor.setName("andrea");
		employee_contractor.setSurname("mognaschi");
		employee_contractor.setUsername("munci");
		employee_contractor.setPassword("munci");
		employee_contractor.setE_mail("a@bi.it");
		employee_contractor.setPhone_number("3331112233");
		employee_contractor.setPostal_address("via roma 1");
		employee_contractor.setHourly_rate(10);
		e_dao.add(employee_contractor);

		TimeCard timecard = new TimeCard();
		timecard.setContractor_employee(employee_contractor);
		timecard.setDate(new Date());
		timecard.setStart_time(new Time(8, 30, 0));
		timecard.setEnd_time(new Time(18, 0, 0));
		timecard.setHours_worked(8);
		timecard_service.registerTimeCard(employee_contractor, timecard);

		Union union = new Union();
		union.setName("CISL");
		union.setPhone_number("3388194740");
		union.setUnion_dues(0.5f);
		union_dao.add(union);
		u_service.setUnion(employee_contractor, u_service.getUnionName(union));

		it.screwdrivers.payroll.model.union.UnionService union_service = new it.screwdrivers.payroll.model.union.UnionService();
		union_service.setDescription("description");
		union_service.setName("test_service");
		us_dao.add(union_service);

		UnionServiceAssociation usa = new UnionServiceAssociation();
		usa.setUnion(union);
		usa.setUnion_service(union_service);
		usa.setPrice(10);
		usa_dao.add(usa);

		List<UnionServiceAssociation> selected_union_service_associations = new ArrayList<UnionServiceAssociation>();
		selected_union_service_associations.add(usa);

		// The employee "bonaz" attempts to order "test_service" from
		// "test_union"
		huc_service.confirmOrder(employee_contractor,
				selected_union_service_associations);

		// pay action for contractors
		PayEngine pay_engine = pay_engine_factory
				.getPayEngine("ContractorEmployee");
		pay_engine.pay();

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		int historical_salaries_size = historical_salaries.size();
		float amount = historical_salaries.get(0).getAmount();

		for (HistoricalSalary hs : historical_salaries) {
			hs_dao.remove(hs);
		}
		tc_dao.remove(timecard);
		List<HistoricalUnionCharge> hucs = huc_dao.findAll();
		for (HistoricalUnionCharge huc : hucs) {
			huc_dao.remove(huc);
		}
		usa_dao.remove(usa);
		us_dao.remove(union_service);
		e_dao.remove(employee_contractor);
		union_dao.remove(union);

		assertTrue(historical_salaries_size == 1);
		assertTrue(amount == 30);
	}

	@SuppressWarnings("deprecation")
	// @Test
	public void testIfCommissionedEmployeesArePaid() {

		int count = 0;

		// pay action for contractors
		PayEngine pay_engine = pay_engine_factory
				.getPayEngine("CommissionedEmployee");

		pay_engine.pay();

		List<HistoricalSalary> historical_salaries = hs_dao.findAll();
		// check if payment is made
		for (HistoricalSalary hs : historical_salaries) {

			if (hs.getEmployee().getClass().getSimpleName()
					.equals("CommissionedEmployee")) {

				// check if i added for today the payment record
				if (hs.getDate().getDate() == calendar_service.getToday()
						.getDate()
						&& hs.getDate().getMonth() == calendar_service
								.getToday().getMonth()
						&& hs.getDate().getYear() == calendar_service
								.getToday().getYear()) {

					count++;
				}
			}
		}

		List<CommissionedEmployee> commissioned_employees = e_dao
				.findAllCommissioned();
		boolean condition = commissioned_employees.size() == count;

		for (HistoricalSalary hs : historical_salaries) {
			hs_dao.remove(hs);
		}

		assertTrue(condition);
	}
}
