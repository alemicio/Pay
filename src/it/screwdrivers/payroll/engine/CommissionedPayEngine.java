package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.controller.SalesCardController;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.card.SalesCard;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

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
	EmployeeDao e_dao;
	@Inject
	HistoricalSalarycontroller h_controller;
	@Inject
	PayrollCalendar p_calendar;
	@Inject
	SalesCardController s_controller;

	
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

		List<SalesCard> s_cards;
		List<Date> working_days = p_calendar.last2WeeksList();
		float total = 0;

		for (CommissionedEmployee c : getCom_employees()) {

				s_cards = s_controller.retriveByEmployee(c);

				for (SalesCard s : s_cards) {

					for (Date wd : working_days) {

						if (wd.getDate()  == s.getDate().getDate() &&
							wd.getMonth() == s.getDate().getMonth()&&
							wd.getYear()  == s.getDate().getYear()) {
							
								total += c.getSale_rate() * s.getAmount();
						}
						break;
					}
				}
				h_controller.registerPay(c, total);

		}
	}

	

}
