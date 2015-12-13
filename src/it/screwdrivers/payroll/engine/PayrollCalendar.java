package it.screwdrivers.payroll.engine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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
	public boolean isLastDayOfYear(){
		
		calendar = new GregorianCalendar();
		if(calendar.get(Calendar.DAY_OF_YEAR) == 365){
			return true;
		}
		
		return false;
		
	}
	
	public List<Calendar> contractorLastWeek(){
		
		calendar = new GregorianCalendar();
		List<Calendar> working_days = new ArrayList<Calendar>();
		
		
		for(int i=0;i<7;i++){
			calendar.add(Calendar.DATE, -i);
			working_days.add(calendar);
		}
				
		return working_days;
		
	}

}
