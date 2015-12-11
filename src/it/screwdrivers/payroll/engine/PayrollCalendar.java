package it.screwdrivers.payroll.engine;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class PayrollCalendar {

	private Calendar calendar;

	public int getCurrentNumberDay() {

		calendar = new GregorianCalendar();

		return calendar.get(Calendar.DAY_OF_YEAR);

	}

	public boolean isFriday() {

		calendar = new GregorianCalendar();

		// check if the day is friday
		if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
			return true;
		}

		return false;
	}

	public boolean d1ChangeMounth() {

		calendar = new GregorianCalendar();

		int d_month = calendar.get(Calendar.MONTH);
		System.out.println(calendar.getTime());
		calendar.add(Calendar.DATE, 1); // set the next day
		System.out.println(calendar.getTime());

		int d1_month = calendar.get(Calendar.MONTH);

		if (d_month != d1_month) {
			return true;
		}

		return false;
	}

	public boolean d3ChangeMounth() {
		calendar = new GregorianCalendar();

		int d_month = calendar.get(Calendar.MONTH);

		calendar.add(Calendar.DATE, 3); // set the next day

		int d3_month = calendar.get(Calendar.MONTH);

		if (d_month != d3_month) {
			return true;
		}

		return false;
		
	}

	public boolean isWeekNumberPair() {
		
		calendar = new GregorianCalendar();
		int week_number = calendar.get(Calendar.WEEK_OF_YEAR);
		
		if(week_number%2 == 0){
			return true;
		}
		
		return false;
	}

}
