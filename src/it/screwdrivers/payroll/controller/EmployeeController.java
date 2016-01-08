package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.EmployeeService;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.model.employee.Employee;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("employee")
@SessionScoped
public class EmployeeController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EmployeeService employee_service;

	@Inject
	HistoricalSalaryService historical_salary_service;

	public void changeDetails(Employee employee) {
		employee_service.changeDetails(employee);
	}
}
