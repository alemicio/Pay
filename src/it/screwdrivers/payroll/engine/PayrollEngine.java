package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.logic.CalendarService;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;

import javax.ejb.Singleton;
import javax.inject.Inject;

@Singleton
public class PayrollEngine {

	@Inject
	PayEngineFactory pay_engine_factory;
	
	@Inject
	CalendarService calendar_service;
	
	PayEngine pay_engine;

	//@Schedule(second="*/6", minute="*",hour="*", persistent=false)
	public void runPayroll() {
		
		System.out.println("Payroll task running now");

		if (calendar_service.isFriday()) {
			System.out.println("is friday");
			//pay contractor employees
			pay_engine = pay_engine_factory.getPayEngine(ContractorEmployee.class.getSimpleName());
			pay_engine.pay();

			if (calendar_service.isWeekNumberPair()) {
				System.out.println("is a pair week");
				//pay commissions sales
				pay_engine = pay_engine_factory.getPayEngine(CommissionedEmployee.class.getSimpleName());
				pay_engine.pay();
			}

			if (calendar_service.d3ChangeMounth()) {
				System.out.println("d+3 change mounth");
				//  pay salaried employees && commissioned standard salary
				pay_engine = pay_engine_factory.getPayEngine(SalariedEmployee.class.getSimpleName());
				pay_engine.pay();
			}
		}
		else{
			if(calendar_service.d1ChangeMounth()){
				System.out.println("d+1 change mounth");
				//pay salaried employees && commissioned standard salary
				pay_engine = pay_engine_factory.getPayEngine(SalariedEmployee.class.getSimpleName());
				pay_engine.pay();
			}
		}
		
		if(calendar_service.isLastDayOfYear()){
			System.out.println("is the last day of the year");
			pay_engine = pay_engine_factory.getPayEngine(EmployeeManager.class.getSimpleName());
			pay_engine.pay();
		}
	}

}
