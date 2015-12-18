package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import java.util.List;

public abstract class PayEngine {
	
	private List<SalariedEmployee> s_employees;
	private List<CommissionedEmployee> com_employees;
	private List<EmployeeManager> m_employees;
	private List<ContractorEmployee> con_employees;
	
	public abstract void pay();
	
	public abstract void initList();

}
