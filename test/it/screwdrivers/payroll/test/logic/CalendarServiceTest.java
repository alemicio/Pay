package it.screwdrivers.payroll.test.logic;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import it.screwdrivers.payroll.logic.CalendarService;
import it.screwdrivers.payroll.test.ArquillianTest;
import it.screwdrivers.payroll.test.DbSeeder;

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
public class CalendarServiceTest {

	CalendarService p_calendar = new CalendarService();

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
	public void testNumberOfDay() {

		int currentDayNumber = p_calendar.getCurrentNumberDay();
		assertEquals(345, currentDayNumber);
	}

	@Test
	public void isFriday() {

		boolean response = false;
		response = p_calendar.isFriday();
		assertTrue(response);
	}

	@Test
	public void d1ChangeMounth() {

		boolean response = false;
		response = p_calendar.d1ChangeMounth();
		assertTrue(response);
	}

	@Test
	public void d3ChangeMounth() {

		boolean response = false;
		response = p_calendar.d3ChangeMounth();
		assertTrue(response);
	}

	@Test
	public void isWeekNumberPair() {

		boolean response = false;
		response = p_calendar.isWeekNumberPair();
		assertTrue(response);
	}

	@Test
	public void dateToDay() {
		System.out.println("TO DAY:");
		System.out.println(p_calendar.getToday());
		System.out.println("\n");
	}

	@Test
	public void lastWeek() {

		List<Date> working_days = new ArrayList<Date>();
		working_days = p_calendar.lastWeekList();
		System.out.println("number of days: \t" + working_days.size());
		for (Date d : working_days) {
			System.out.println(d);
		}

		System.out.println("\n");
	}

	@Test
	public void last2Week() {

		List<Date> working_days = new ArrayList<Date>();
		working_days = p_calendar.last2WeeksList();
		System.out.println("number of days: \t" + working_days.size());
		for (Date d : working_days) {
			System.out.println(d);
		}

		System.out.println("\n");
	}

	// The following is not properly a test since
	// the check is not done automatically but
	// we have to do it. The aim of this test method
	// is to print the output list of
	// CalendarService's lastMonthList method
	@Test
	public void printLastMonthList() {

		List<Date> working_days = p_calendar.lastMonthList();
		System.out.println("==========================================");
		System.out.println("==== CalendarService - lastMonthList =====");
		for (Date d : working_days) {
			System.out.println(d);
		}
		System.out.println("==========================================\n");
	}
}
