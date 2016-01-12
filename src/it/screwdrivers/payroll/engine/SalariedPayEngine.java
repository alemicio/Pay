package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.logic.HistoricalUnionChargeService;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named("salariedEngine")
@Stateless
public class SalariedPayEngine extends PayEngine {
	
	@Inject
	HistoricalSalaryService hs_service;
	
	@Inject
	HistoricalUnionChargeService huc_service;
	
	@Inject
	EmployeeDao e_dao;

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
				hs_service.registerPay(s);
			} else {
				// Compute union's fixed dues
				dues = s.getMonthly_salary() * s.getUnion().getUnion_dues();
				
				// Compute charges associated to the requested union's services
				total_charges = huc_service.getLastMonthUnionTotalChargesByEmployee(s);
				
				hs_service.registerPay(s, (total_charges + dues));
			}
		}
	}
}
