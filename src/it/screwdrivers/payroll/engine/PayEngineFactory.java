package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

public class PayEngineFactory {

	public IPayEngine getPayEngine(String employee_class) {

		if (employee_class == null) {
			return null;
		}
		if (employee_class.equals("CommissionedEmployee") ) {
			return new CommissionedPayEngine();

		} else if (employee_class.equals("ContractorEmployee")) {
			return new ContractorPayEngine();

		} else if (employee_class.equals("SalariedEmployee")) {
			return new SalariedPayEngine();
			
		} else if (employee_class.equals("EmployeeManager")) {
			return new ManagerPayEngine();
		}
		
		return null;

	}

}
