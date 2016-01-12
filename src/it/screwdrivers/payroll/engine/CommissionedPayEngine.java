package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.logic.CalendarService;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.logic.SalesCardService;
import it.screwdrivers.payroll.model.card.SalesCard;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;

import java.sql.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Named("commissionedEngine")
@Stateless
public class CommissionedPayEngine extends PayEngine {

	@Inject
	SalesCardService sc_service;

	@Inject
	HistoricalSalaryService hs_service;

	@Inject
	EmployeeDao e_dao;

	@Inject
	CalendarService calendar_service;

	public CommissionedPayEngine() {
		super();
	}

	@PostConstruct
	@Override
	public void initList() {
		setCom_employees(e_dao.findAllCommissioned());
	}

	@SuppressWarnings("deprecation")
	@Override
	public void pay() {

		List<SalesCard> sales_cards;
		List<Date> working_days = calendar_service.last2WeeksList();
		float total = 0;

		for (CommissionedEmployee ce : getCom_employees()) {
			sales_cards = sc_service.retriveByEmployee(ce);

			for (SalesCard sc : sales_cards) {
				for (Date wd : working_days) {
					if (wd.getDate() == sc.getDate().getDate()
							&& wd.getMonth() == sc.getDate().getMonth()
							&& wd.getYear() == sc.getDate().getYear()) {

						total += ce.getSale_rate() * sc.getAmount();
					}
					
					break;
				}
			}

			hs_service.registerPay(ce, total);
		}
	}
}
