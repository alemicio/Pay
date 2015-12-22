package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.controller.HistoricalUnionChargeController;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named("salariedEngine")
@Stateless
public class SalariedPayEngine extends PayEngine {

	@Inject
	EmployeeDao e_dao;
	@Inject
	HistoricalSalarycontroller h_controller;
	@Inject
	HistoricalUnionChargeController huc_controller;

	public SalariedPayEngine() {
		super();
	}

	@PostConstruct
	@Override
	public void initList() {
		setS_employees(e_dao.findAllSalaried());
	}

	@Override
	public void pay() {

		float total_charges = 0;
		float dues = 0;
		

		for (SalariedEmployee s : getS_employees()) {

			if (s.getUnion() == null) {
				h_controller.registerPay(s);
			} else {
				dues = s.getMonthly_salary() * s.getUnion().getUnion_dues();
				
				total_charges = huc_controller.UnionChargeByEmployee(s);
				
				h_controller.registerPay(s, (total_charges + dues));
			}
		}
	}
	
	
}
