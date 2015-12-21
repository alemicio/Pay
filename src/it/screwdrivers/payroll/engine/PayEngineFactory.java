package it.screwdrivers.payroll.engine;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

@Stateless
public class PayEngineFactory {
	
//	@Inject
//	CommissionedPayEngine commissioned_pe;
//	@Inject
//	ContractorPayEngine contractor_pe;
	@Inject
	SalariedPayEngine salaried_pe;
//	@Inject
//	ManagerPayEngine manager_pe;


	public PayEngine getPayEngine(String employee_class) {
		
		if (employee_class == null) {
			return null;
		}
		 
		if (employee_class.equals("SalariedEmployee") ) {
			return salaried_pe;
		}
//		} else if (employee_class.equals("ContractorEmployee")) {
//			return new ContractorPayEngine();
//
//		} else if (employee_class.equals("CommissionedEmployee")) {
//			return new CommissionedPayEngine();
//			
//		} else if (employee_class.equals("EmployeeManager")) {
//			return new ManagerPayEngine();
//		}
		
		return null;

	}

}
