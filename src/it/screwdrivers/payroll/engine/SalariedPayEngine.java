package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.controller.HistoricalUnionChargeController;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SalariedPayEngine extends PayEngine {

	@Inject
	EmployeeDao e_dao;
	@Inject
	HistoricalSalarycontroller h_controller;
	@Inject
	HistoricalUnionChargeController huc_controller;

	@PostConstruct
	public void initList() {

	}


	@Override
	public void pay() {
		System.out.println("dio porco");

		List<SalariedEmployee> s_employees = e_dao.findAllSalaried();

		System.out.println(s_employees.toString());
		//
		// float total_charges = 0;
		// float dues = 0;
		//
		// for (SalariedEmployee s : s_employees) {
		//
		// if (s.getUnion() == null) {
		// h_controller.registerPay(s);
		// }
		// else {
		// dues = s.getMonthly_salary() * s.getUnion().getUnion_dues();
		// total_charges = huc_controller.UnionChargeByEmployee(s);
		// h_controller.registerPay(s, (total_charges + dues));
		//
		// }
		//
		// }

	}

}
