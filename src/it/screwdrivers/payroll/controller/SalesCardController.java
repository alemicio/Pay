package it.screwdrivers.payroll.controller;

import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.SalesCardDao;
import it.screwdrivers.payroll.pojo.card.SalesCard;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SalesCardController {

	@Inject
	SalesCardDao s_dao;

	@SuppressWarnings("deprecation")
	public String registerTimeCard(CommissionedEmployee commissioned_employee,
			SalesCard sales_card) {

		List<SalesCard> salescards = s_dao.findAll();

		// check if the user tries to send a salecard that is already sent from
		// the specific customer
		// on a specific date
		for (SalesCard s : salescards) {

			if (s.getCommissioned_employee().getId() == commissioned_employee
					.getId()) {

				// check if there is already a salescard that refers to the
				// given customer on that date
				if (s.getCustomer().equals(sales_card.getCustomer())) {
					if (s.getDate().getDay() == sales_card.getDate().getDay()
						&& s.getDate().getMonth() == sales_card.getDate().getMonth()
						&& s.getDate().getYear() == sales_card.getDate().getYear()) {
						
						return "failed";
					}
				}

			}
		}

		s_dao.add(sales_card);
		return "success";

	}
}
