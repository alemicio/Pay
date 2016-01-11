package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.model.historical.HistoricalSalary;

import java.io.Serializable;
import java.util.List;

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

	public List<HistoricalSalary> getHistorical_salaries() {
		
		// This is the list of all the historical salaries associated to the
		// current EmployeeManager user
		List<HistoricalSalary> historical_salaries = historical_salary_service
				.getHistoricalSalariesByEmployeeId(login_controller
						.getLogged_employee().getId());
		
		return historical_salaries;
	}
}
