package it.screwdrivers.payroll.engine;

import java.util.List;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.controller.HistoricalUnionChargeController;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named("managerEngine")
@Stateless
public class ManagerPayEngine extends PayEngine {

	@Inject
	EmployeeDao e_dao;
	@Inject
	HistoricalUnionChargeController huc_controller;
	@Inject
	HistoricalSalarycontroller h_controller;

	public ManagerPayEngine() {
		super();
	}

	@Override
	public void pay() {

		List<EmployeeManager> m_employees = e_dao.findAllManager();
		float dues = 0;
		float total_charges = 0;

		for (EmployeeManager m : m_employees) {

			if(m.getUnion() == null){
				h_controller.registerPay(m);
			}
			else {
				dues = m.getAnnual_rate()* m.getUnion().getUnion_dues();
				total_charges = huc_controller.UnionChargeByEmployee(m);
				h_controller.registerPay(m, (total_charges+dues) );
				
			}

		}

	}

	@Override
	public void initList() {
		// TODO Auto-generated method stub
		
	}

}
