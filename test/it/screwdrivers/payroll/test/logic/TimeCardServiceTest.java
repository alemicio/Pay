package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.assertEquals;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.TimeCardDao;
import it.screwdrivers.payroll.logic.TimeCardService;
import it.screwdrivers.payroll.model.card.TimeCard;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.test.ArquillianTest;
import it.screwdrivers.payroll.test.DbSeeder;

import java.sql.Time;
import java.util.Date;

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
public class TimeCardServiceTest {

	@Inject
	TimeCardService timecard_service;

	@Inject
	EmployeeDao employee_dao;

	@Inject
	TimeCardDao timecard_dao;

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
	public void testRegisterTimeCard() {

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

		TimeCard timecard = new TimeCard();
		timecard.setContractor_employee(employee1);
		timecard.setDate(new Date());
		timecard.setStart_time(new Time(8, 30, 0));
		timecard.setEnd_time(new Time(18, 0, 0));
		timecard.setHours_worked(8);

		TimeCard timecard2 = new TimeCard();
		timecard2.setContractor_employee(employee1);
		timecard2.setDate(new Date());
		timecard2.setStart_time(new Time(8, 30, 0));
		timecard2.setEnd_time(new Time(18, 0, 0));
		timecard2.setHours_worked(8);

		String first_outcome = timecard_service.registerTimeCard(employee1,
				timecard);
		String second_outcome = timecard_service.registerTimeCard(employee1,
				timecard);

		timecard_dao.remove(timecard);
		timecard_dao.remove(timecard2);
		employee_dao.remove(employee1);

		assertEquals(first_outcome, "success");
		assertEquals(second_outcome, "failed");
	}
}
