package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class PayrollEngine {

	@Inject
	PayEngineFactory pay_engine_factory;
	@Inject
	PayrollCalendar c;
	
	PayEngine pay_engine;
	

	//@Schedule(second="*/6", minute="*",hour="*", persistent=false)
	public void runPayroll() {
		
		System.out.println("Payroll task running now");

		if (c.isFriday()) {
			
			System.out.println("is friday");
			//pay contractor employees
			pay_engine = pay_engine_factory.getPayEngine(ContractorEmployee.class.getSimpleName());
			pay_engine.pay();

			if (c.isWeekNumberPair()) {
				
				System.out.println("is a pair week");
				//pay commissions sales
				pay_engine = pay_engine_factory.getPayEngine(CommissionedEmployee.class.getSimpleName());
				pay_engine.pay();
			}

			if (c.d3ChangeMounth()) {
				
				System.out.println("d+3 change mounth");
				//  pay salaried employees && commissioned standard salary
				pay_engine = pay_engine_factory.getPayEngine(SalariedEmployee.class.getSimpleName());
				pay_engine.pay();
				
			}
		}
		else{
			
			if(c.d1ChangeMounth()){
				
				System.out.println("d+1 change mounth");
				//pay salaried employees && commissioned standard salary
				pay_engine = pay_engine_factory.getPayEngine(SalariedEmployee.class.getSimpleName());
				pay_engine.pay();
			}
		}
		
		if(c.isLastDayOfYear()){
			
			System.out.println("is the last day of the year");
			// TODO pay manager
			pay_engine = pay_engine_factory.getPayEngine(EmployeeManager.class.getSimpleName());
			pay_engine.pay();
		}
	}

}
