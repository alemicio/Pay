package it.screwdrivers.payroll.engine;

import it.screwdrivers.payroll.controller.HistoricalSalarycontroller;
import it.screwdrivers.payroll.controller.SalesCardController;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.pojo.card.SalesCard;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

public class CommissionedPayEngine implements IPayEngine {

	@Inject
	EmployeeDao e_dao;
	@Inject
	HistoricalSalarycontroller h_controller;
	@Inject
	PayrollCalendar p_calendar;
	@Inject
	SalesCardController s_controller;

	@Override
	public void pay() {

		List<CommissionedEmployee> c_employees = e_dao.findAllCommissioned();
		List<SalesCard> s_cards;
		List<Date> working_days = p_calendar.last2WeeksList();
		float total = 0;

		for (CommissionedEmployee c : c_employees) {

				s_cards = s_controller.retriveByEmployee(c);

				for (SalesCard s : s_cards) {

					for (Date wd : working_days) {

						if (wd == s.getDate()) {
							total += c.getSale_rate() * s.getAmount();
						}
						break;
					}
				}
				h_controller.registerPay(c, total);

		}
	}

}
