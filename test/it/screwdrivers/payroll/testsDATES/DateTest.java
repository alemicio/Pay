package it.screwdrivers.payroll.testsDATES;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.inject.Inject;

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
		System.out.println(p_calendar.getToday());
		
		
	}

}
