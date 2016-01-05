package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public abstract class PayEngine {

	private List<SalariedEmployee> s_employees;
	private List<CommissionedEmployee> com_employees;
	private List<EmployeeManager> m_employees;
	private List<ContractorEmployee> con_employees;

	public abstract void pay();

	public abstract void initList();

	public List<SalariedEmployee> getS_employees() {
		return s_employees;
	}

	public void setS_employees(List<SalariedEmployee> s_employees) {
		this.s_employees = s_employees;
	}

	public List<CommissionedEmployee> getCom_employees() {
		return com_employees;
	}

	public void setCom_employees(List<CommissionedEmployee> com_employees) {
		this.com_employees = com_employees;
	}

	public List<EmployeeManager> getM_employees() {
		return m_employees;
	}

	public void setM_employees(List<EmployeeManager> m_employees) {
		this.m_employees = m_employees;
	}

	public List<ContractorEmployee> getCon_employees() {
		return con_employees;
	}

	public void setCon_employees(List<ContractorEmployee> con_employees) {
		this.con_employees = con_employees;
	}
	
	

}
