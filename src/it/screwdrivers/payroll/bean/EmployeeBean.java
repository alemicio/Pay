package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.EmployeeController;
import it.screwdrivers.payroll.pojo.employee.Employee;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("Employee")
@SessionScoped
public class EmployeeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EmployeeController e_controller;

	private Employee retrived_employee;

	public Employee retrieveEmployee(String username, String password) {

		retrived_employee = e_controller.retrieveEmployee(username, password);
		return retrived_employee;
	}

	public Employee getRetrived_employee() {
		return retrived_employee;
	}

	public void setRetrived_employee(Employee retrived_employee) {
		this.retrived_employee = retrived_employee;
	}

}
