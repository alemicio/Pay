package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

public class PayrollEngine {

	PayEngineFactory pay_engine_factory = new PayEngineFactory();
	IPayEngine pay_engine;
	PayrollCalendar c = new PayrollCalendar();

	public void runPayroll() {

		if (c.isFriday()) {

			//  pay contractor employees
			pay_engine = pay_engine_factory.getPayEngine(ContractorEmployee.class.getSimpleName());
			pay_engine.pay();

			if (c.isWeekNumberPair()) {
				
				//pay commissions sales
				pay_engine = pay_engine_factory.getPayEngine(CommissionedEmployee.class.getSimpleName());
				pay_engine.pay();
			}

			if (c.d3ChangeMounth()) {
				//  pay salaried employees && commissioned standard salary
				pay_engine = pay_engine_factory.getPayEngine(SalariedEmployee.class.getSimpleName());
				pay_engine.pay();
				
			}
		}
		else{
			
			if(c.d1ChangeMounth()){
				//pay salaried employees && commissioned standard salary
				pay_engine = pay_engine_factory.getPayEngine(SalariedEmployee.class.getSimpleName());
				pay_engine.pay();
			}
		}
		
		if(c.isLastDayOfYear()){
			// TODO pay manager
			pay_engine = pay_engine_factory.getPayEngine(EmployeeManager.class.getSimpleName());
			pay_engine.pay();
		}
	}

}
