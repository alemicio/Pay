package it.screwdrivers.payroll.engine;

public class PayrollEngine {

	PayEngineFactory pay_engine_factory = new PayEngineFactory();
	IPayEngine pay_engine;
	PayrollCalendar c = new PayrollCalendar();

	public void runPayroll() {

		if (c.isFriday()) {

			// TODO pay contractor employees

			if (c.isWeekNumberPair()) {
				// TODO pay commisioned employees commissions
			}

			if (c.d3ChangeMounth()) {
				// TODO pay salaried employees
				// TODO Pat commissioned standard salary
			}
		}
		else{
			
			if(c.d1ChangeMounth()){
				// TODO pay salaried employees
				// TODO Pat commissioned standard salary
			}
		}
	}

}
