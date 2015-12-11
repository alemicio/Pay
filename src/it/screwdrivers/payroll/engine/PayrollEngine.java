package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;

public class PayrollEngine {

	PayEngineFactory pay_engine_factory = new PayEngineFactory();
	IPayEngine pay_engine;
	PayrollCalendar c = new PayrollCalendar();

	public void runPayroll() {

		if (c.isFriday()) {

			// TODO pay contractor employees
			pay_engine = pay_engine_factory.getPayEngine(ContractorEmployee.class.getSimpleName());
			pay_engine.pay();

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
		
		if(c.isLastDayOfYear()){
			// TODO pay manager
		}
	}

}
