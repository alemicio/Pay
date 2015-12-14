package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import java.util.List;

import javax.inject.Inject;

public class SalariedPayEngine implements IPayEngine {

	@Inject
	EmployeeDao e_dao;
	@Inject
	HistoricalSalarycontroller h_controller;

	@Override
	public void pay() {

		paySalaried();
		payCommissioned();

	}

	
	private void payCommissioned() {
		List<CommissionedEmployee> c_employees = e_dao.findAllCommissioned();
		
		for(CommissionedEmployee c: c_employees){
			
			h_controller.registerPay(c);
		
		}
	}

	
	private void paySalaried() {
		List<SalariedEmployee> s_employees = e_dao.findAllSalaried();

		for (SalariedEmployee s : s_employees) {

			h_controller.registerPay(s);

		}
	}

}
