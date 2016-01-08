package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.model.historical.HistoricalSalary;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("historical_salary")
@SessionScoped
public class HistoricalSalaryController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject 
	LoginController login_controller;

	@Inject
	HistoricalSalaryService historical_salary_service;

	private List<HistoricalSalary> historical_salaries;

	@PostConstruct
	public void init() {
		// This is the list of all the historical salaries associated to the
		// current EmployeeManager user
		historical_salaries = historical_salary_service
				.getHistoricalSalariesByEmployeeId(login_controller.getLogged_employee().getId());
	}

	public List<HistoricalSalary> getHistorical_salaries() {
		return historical_salaries;
	}
}