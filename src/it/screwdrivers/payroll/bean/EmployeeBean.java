package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.EmployeeController;
import it.screwdrivers.payroll.pojo.employee.Employee;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("employee")
@SessionScoped
public class EmployeeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EmployeeController e_controller;

	private Employee retrieved_employee;
	private Employee updated_employee;
	
	public Employee retrieveEmployee(String username, String password) {

		retrieved_employee = e_controller.retrieveEmployee(username, password);
		return retrieved_employee;
	}

	public Employee getRetrieved_employee() {
		return retrieved_employee;
	}

	public void setRetrieved_employee(Employee retrieved_employee) {
		this.retrieved_employee = retrieved_employee;
	}
}
