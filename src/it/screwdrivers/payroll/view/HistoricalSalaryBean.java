package it.screwdrivers.payroll.view;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.model.historical.HistoricalSalary;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("historical_salary")
@SessionScoped
public class HistoricalSalaryBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EmployeeBean e_bean;

	@Inject
	HistoricalSalarycontroller hs_controller;

	private List<HistoricalSalary> historical_salaries;

	@PostConstruct
	public void init() {

		// This is the list of all the historical salaries associated to the
		// current EmployeeManager user
		historical_salaries = hs_controller
				.getHistoricalSalariesByEmployeeId(e_bean.getRetrieved_employee().getId());
	}

	public List<HistoricalSalary> getHistorical_salaries() {
		return historical_salaries;
	}

}
