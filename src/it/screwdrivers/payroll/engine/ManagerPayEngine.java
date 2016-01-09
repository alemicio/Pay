package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.logic.HistoricalUnionChargeService;
import it.screwdrivers.payroll.model.employee.EmployeeManager;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named("managerEngine")
@Stateless
public class ManagerPayEngine extends PayEngine {

	@Inject
	HistoricalUnionChargeService huc_service;

	@Inject
	HistoricalSalaryService hs_service;

	@Inject
	EmployeeDao e_dao;

	public ManagerPayEngine() {
		super();
	}

	// Since the ManagerPayEngine object is created,
	// this method will be called. It fills the list
	// of Manager employees that we can find inside
	// the PayEngine abstract class
	@PostConstruct
	@Override
	public void initList() {
		setM_employees(e_dao.findAllManager());
	}

	@Override
	public void pay() {

		float dues = 0;
		float total_charges = 0;

		// For each manager employee we check if he
		// belongs to a union. If he does not belong
		// to a union we perform registerPay; otherwise,
		// we have also to compute dues and total_charges
		for (EmployeeManager m : getM_employees()) {

			if (m.getUnion() == null) {
				hs_service.registerPay(m);
			} else {
				dues = m.getAnnual_rate() * m.getUnion().getUnion_dues();
				total_charges = huc_service.getLastMonthUnionTotalChargesByEmployee(m);

				hs_service.registerPay(m, (total_charges + dues));
			}
		}
	}
}
