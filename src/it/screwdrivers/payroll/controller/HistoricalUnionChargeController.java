package it.screwdrivers.payroll.controller;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import it.screwdrivers.payroll.dao.HistoricalUnionChargeDao;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.historical.HistoricalUnionCharge;
import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class HistoricalUnionChargeController {

	@Inject
	HistoricalUnionChargeDao huc_dao;

	public String confirmOrder(Employee e,
			List<UnionServiceAssociation> selected_union_service_associations) {

		String response = null;
		

		// get the current system date and time(week number)
		Calendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
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
			} 
			else {
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
						response+=","+service_name;
					}
				}
				
				if(disableOrdering == false){
					
					HistoricalUnionCharge huc = new HistoricalUnionCharge();
					huc.setUnion_service_association(susa);
					huc.setEmployee(e);
					huc.setDate(date);

					huc_dao.add(huc);
				}

				
			}
		}

		response+=",success";
		return response;
	}

}
