package it.screwdrivers.payroll.testsDATES;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import it.screwdrivers.payroll.engine.PayrollCalendar;
import it.screwdrivers.payroll.testsDB.ArquillianTest;

import org.junit.Test;

public class DateTest extends ArquillianTest {
	
	PayrollCalendar p_calendar = new PayrollCalendar();
	
	@Test
	public void testNumberOfDay() {
		
		
		int currentDayNumber = p_calendar.getCurrentNumberDay();

		assertEquals(345, currentDayNumber);
		
	}
	@Test
	public void isFriday(){
		
		boolean response = false;
		
		response= p_calendar.isFriday();
		
		assertTrue(response);
	}
	
	@Test
	public void d1ChangeMounth(){
		
		boolean response = false;
		
		response = p_calendar.d1ChangeMounth();
		
		assertTrue(response);
		
	}
	@Test
	public void d3ChangeMounth(){
		
		boolean response = false;
		
		response = p_calendar.d3ChangeMounth();
		
		assertTrue(response);
		
	}
	@Test
	public void isWeekNumberPair(){
		
		boolean response = false;
		
		response = p_calendar.isWeekNumberPair();
		
		assertTrue(response);
		
	}
	
	@Test
	public void dateToDay(){
		System.out.println("TO DAY:");
		System.out.println(p_calendar.getToday());
		System.out.println("\n");
	}
	
	@Test
	public void lastWeek(){
		
		List<Date> working_days = new ArrayList<Date>();
		working_days = p_calendar.lastWeekList();
		System.out.println("number of days: \t" + working_days.size());
		for(Date d: working_days){
			System.out.println(d);
		}
		
		System.out.println("\n");
	}
	@Test
	public void last2Week(){
		
		List<Date> working_days = new ArrayList<Date>();
		working_days = p_calendar.last2WeeksList();
		System.out.println("number of days: \t" + working_days.size());
		for(Date d: working_days){
			System.out.println(d);
		}
		
		System.out.println("\n");
	}
}
