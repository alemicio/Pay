package it.screwdrivers.payroll.engine;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class PayEngineFactory {
	
	@Inject
	CommissionedPayEngine commissioned_pe;
	@Inject
	ContractorPayEngine contractor_pe;
	@Inject
	SalariedPayEngine salaried_pe;
	@Inject
	ManagerPayEngine manager_pe;


	public PayEngine getPayEngine(String employee_class) {
		
		if (employee_class == null) {
			return null;
		}
		 
		if (employee_class.equals("SalariedEmployee") ) {
			return salaried_pe;
		} else if (employee_class.equals("ContractorEmployee")) {
			return contractor_pe;

		} else if (employee_class.equals("CommissionedEmployee")) {
			return commissioned_pe;
			
		} else if (employee_class.equals("EmployeeManager")) {
			return manager_pe;
		}
		
		return null;

	}

}
