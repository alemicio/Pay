package it.screwdrivers.payroll.engine;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PayrollEngine {
	
	PayEngineFactory pay_engine_factory = new PayEngineFactory();
	IPayEngine pay_engine;
	PayrollCalendar c = new PayrollCalendar();
	
	public void runPayroll(){
		
		if(c.isFriday()){
			
		}
	}

}
