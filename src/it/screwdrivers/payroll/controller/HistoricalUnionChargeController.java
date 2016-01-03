package it.screwdrivers.payroll.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalUnionChargeDao;
import it.screwdrivers.payroll.engine.PayrollCalendar;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.historical.HistoricalUnionCharge;
import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HistoricalUnionChargeController {

	@Inject
	HistoricalUnionChargeDao huc_dao;
	@Inject
	EmployeeDao e_dao;
	@Inject
	PayrollCalendar p_calendar;

	public String confirmOrder(Employee e,
			List<UnionServiceAssociation> selected_union_service_associations) {

		String response = null;

		// get the current system date and time(week number)
		Calendar calendar = new GregorianCalendar();
		java.util.Date date = calendar.getTime();
		int current_week_number = calendar.get(Calendar.WEEK_OF_YEAR);

		List<HistoricalUnionCharge> huc_list = huc_dao.findAll();

		for (UnionServiceAssociation susa : selected_union_service_associations) {

			boolean disableOrdering = false;

			// service name i of the list
			String service_name = susa.getUnion_service().getName();

			// check if the table if empty
			if (huc_list.size() == 0) {
				HistoricalUnionCharge huc = new HistoricalUnionCharge();
				huc.setUnion_service_association(susa);
				huc.setEmployee(e);
				huc.setDate(date);

				huc_dao.add(huc);
				
			} else {
				// check the order service with the one in the db
				for (HistoricalUnionCharge h : huc_list) {

					// service name i of the db
					String service_name_indb = h.getUnion_service_association()
							.getUnion_service().getName();

					// get week number of the service i in the db
					Calendar service_calendar = Calendar.getInstance();
					service_calendar.setTime(h.getDate());
					int service_week_number = service_calendar
							.get(Calendar.WEEK_OF_YEAR);

					// check if the service is already requested in the current
					// week for the given employee
					if (service_name.equals(service_name_indb)
							&& e.getId() == h.getEmployee().getId()
							&& current_week_number == service_week_number) {

						disableOrdering = true;
						response += "," + service_name;
					}
				}

				if (disableOrdering == false) {

					HistoricalUnionCharge huc = new HistoricalUnionCharge();
					huc.setUnion_service_association(susa);
					huc.setEmployee(e);
					huc.setDate(date);

					huc_dao.add(huc);
				}
			}
		}

		response += ",success";
		return response;
	}

	@SuppressWarnings("deprecation")
	public float UnionChargeByEmployee(Employee e) {

		List<Date> working_days = p_calendar.lastMonthList();
		List<HistoricalUnionCharge> hucs = retrieveUnionServiceChargeByEmployee(e);
		
		float total_charges = 0;

		if (hucs != null) {
			for (HistoricalUnionCharge huc : hucs) {
				for (Date wd : working_days) {
					
					if (wd.getDate()  == huc.getDate().getDate() &&
						wd.getMonth() == huc.getDate().getMonth()&&
						wd.getYear()  == huc.getDate().getYear()){
						
						System.out.println(huc.getUnion_service_association().getPrice());

						total_charges += huc.getUnion_service_association().getPrice();
						break;
					}
				}
			}
		} else {
			total_charges = 0;
		}

		return total_charges;
	}

	private List<HistoricalUnionCharge> retrieveUnionServiceChargeByEmployee(Employee e) {

		List<HistoricalUnionCharge> hucs = huc_dao.findAll();
		List<HistoricalUnionCharge> retrieved_hucs = new ArrayList<HistoricalUnionCharge>();
		
		for (HistoricalUnionCharge huc : hucs) {
			

			if (huc.getEmployee().getId() == e.getId()) {
				retrieved_hucs.add(huc);
			}
		}

		return retrieved_hucs;
	}
}
