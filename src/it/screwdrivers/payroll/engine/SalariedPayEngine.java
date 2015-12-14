package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import java.util.List;

import javax.inject.Inject;

public class SalariedPayEngine implements IPayEngine{

	@Inject
	EmployeeDao e_dao;
	@Inject
	HistoricalSalarycontroller h_controller;

	@Override
	public void pay() {

		List<SalariedEmployee> s_employees = e_dao.findAllSalaried();
		
		for(SalariedEmployee s: s_employees){
			
			h_controller.registerPay(s);

		}

	}

}
