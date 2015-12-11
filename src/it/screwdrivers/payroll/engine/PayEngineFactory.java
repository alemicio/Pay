package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

public class PayEngineFactory {

	public IPayEngine getPayEngine(Employee employee) {

		if (employee == null) {
			return null;
		}
		if (employee instanceof CommissionedEmployee) {
			return new CommissionedPayEngine();

		} else if (employee instanceof ContractorEmployee) {
			return new ContractorPayEngine();

		} else if (employee instanceof SalariedEmployee) {
			return new SalariedPayEngine();
			
		} else if (employee instanceof EmployeeManager) {
			return new ManagerPayEngine();
		}
		
		return null;

	}

}
