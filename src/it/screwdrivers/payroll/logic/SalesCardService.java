package it.screwdrivers.payroll.logic;

import it.screwdrivers.payroll.dao.SalesCardDao;
import it.screwdrivers.payroll.model.card.SalesCard;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.Employee;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class SalesCardService {

	@Inject
	CalendarService calendar_service;
	
	@Inject
	SalesCardDao s_dao;

	@SuppressWarnings("deprecation")
	public String registerTimeCard(CommissionedEmployee commissioned_employee,
			SalesCard sales_card) {

		// If it is saturday or sunday, the sales card cannot be registered
		if (!calendar_service.isSaturday() || !calendar_service.isSunday()) {
			
			List<SalesCard> salescards = s_dao.findAll();

			// It checks whether the given commissioned employee has already
			// sent
			// a SalesCard, specifying the same customer, in the date specified
			// in the given sales_card. In that case the method will return a
			// "failed" string; otherwise, it will return a "success" string and
			// will register the given sales_card
			for (SalesCard s : salescards) {
				if (s.getCommissioned_employee().getId() == commissioned_employee
						.getId()) {
					if (s.getCustomer().equals(sales_card.getCustomer())) {
						if (s.getDate().getDay() == sales_card.getDate()
								.getDay()
								&& s.getDate().getMonth() == sales_card
										.getDate().getMonth()
								&& s.getDate().getYear() == sales_card
										.getDate().getYear()) {

							return "failed";
						}
					}

				}
			}

			s_dao.add(sales_card);
			return "success";

		} else {
			return "failed";
		}
	}

	public List<SalesCard> retriveByEmployee(Employee e) {
		List<SalesCard> sales_cards = s_dao.findAll();
		List<SalesCard> retrieved = new ArrayList<SalesCard>();

		for (SalesCard s : sales_cards) {
			if (s.getCommissioned_employee().getId() == e.getId()) {
				retrieved.add(s);
			}
		}

		return retrieved;
	}

}
