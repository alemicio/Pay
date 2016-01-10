package it.screwdrivers.payroll.logic;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import it.screwdrivers.payroll.dao.EmployeeDao;
import it.screwdrivers.payroll.dao.HistoricalUnionChargeDao;
import it.screwdrivers.payroll.model.employee.Employee;
import it.screwdrivers.payroll.model.historical.HistoricalUnionCharge;
import it.screwdrivers.payroll.model.union.UnionServiceAssociation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HistoricalUnionChargeService {

	@Inject
	CalendarService calendar_service;

	@Inject
	HistoricalUnionChargeDao huc_dao;

	@Inject
	EmployeeDao e_dao;

	public String confirmOrder(Employee e,
			List<UnionServiceAssociation> selected_union_service_associations) {

		// Return string of the method
		String response = "";

		Calendar calendar = new GregorianCalendar();

		// If it is saturday or sunday, the order cannot be registered
		if (!calendar_service.isSaturday() || !calendar_service.isSunday()) {

			// get the current system date and time(week number)
			java.util.Date date = calendar.getTime();
			int current_week_number = calendar.get(Calendar.WEEK_OF_YEAR);

			List<HistoricalUnionCharge> huc_list = huc_dao.findAll();

			for (UnionServiceAssociation susa : selected_union_service_associations) {

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

					boolean is_order_invalid = false;

					for (HistoricalUnionCharge h : huc_list) {
						// name of the service given the historical union charge
						String service_name_indb = h
								.getUnion_service_association()
								.getUnion_service().getName();

						// get current week of year
						Calendar service_calendar = Calendar.getInstance();
						service_calendar.setTime(h.getDate());
						int service_week_number = service_calendar
								.get(Calendar.WEEK_OF_YEAR);

						// check if the service was already requested in the
						// current
						// week by the given employee
						if (service_name.equals(service_name_indb)
								&& e.getId() == h.getEmployee().getId()
								&& current_week_number == service_week_number) {

							is_order_invalid = true;
							response += service_name;
							response += ",";

						}
					}

					if (!is_order_invalid) {
						HistoricalUnionCharge huc = new HistoricalUnionCharge();
						huc.setUnion_service_association(susa);
						huc.setEmployee(e);
						huc.setDate(date);
						huc_dao.add(huc);
					}
				}
			}
		} else {
			response = "no-working-day";
		}

		if (response == "")
			response = "success";

		return response;
	}

	public float getLastMonthUnionTotalChargesByEmployee(Employee e) {
		List<Date> working_days = calendar_service.lastMonthList();
		List<HistoricalUnionCharge> hucs = getUnionServiceChargeByEmployee(e);

		float total_charges = 0;

		if (hucs != null) {
			for (HistoricalUnionCharge huc : hucs) {
				for (Date wd : working_days) {

					// Formatted as YYYY-MM-DD
					String working_day_string = wd.toString();
					String[] splitted_working_day_string = working_day_string
							.split("-");
					String[] splitted_huc_date_string = huc.getDate()
							.toString().split("-");

					if (splitted_working_day_string[0] == splitted_huc_date_string[0]
							&& splitted_working_day_string[1] == splitted_huc_date_string[1]
							&& splitted_working_day_string[2].split(" ")[0] == splitted_huc_date_string[2]
									.split(" ")[0]) {

						total_charges += huc.getUnion_service_association()
								.getPrice();
						break;
					}
				}
			}
		} else {
			total_charges = 0;
		}

		return total_charges;
	}

	private List<HistoricalUnionCharge> getUnionServiceChargeByEmployee(
			Employee e) {
		List<HistoricalUnionCharge> hucs = huc_dao.findAll();
		List<HistoricalUnionCharge> retrieved_hucs = new ArrayList<HistoricalUnionCharge>();

		for (HistoricalUnionCharge huc : hucs) {
			if (huc.getEmployee().getId() == e.getId())
				retrieved_hucs.add(huc);
		}

		return retrieved_hucs;
	}
}
