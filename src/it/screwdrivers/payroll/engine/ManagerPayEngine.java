package it.screwdrivers.payroll.engine;

import java.util.List;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;

import javax.inject.Inject;

public class ManagerPayEngine implements IPayEngine {

	@Inject
	EmployeeDao e_dao;
	@Inject
	HistoricalSalarycontroller h_controller;

	@Override
	public void pay() {

		List<EmployeeManager> m_employees = e_dao.findAllManager();

		for (EmployeeManager m : m_employees) {

			h_controller.registerPay(m);

		}

	}

}
