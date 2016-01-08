package it.screwdrivers.payroll.logic;

import it.screwdrivers.payroll.dao.TimeCardDao;
import it.screwdrivers.payroll.model.card.TimeCard;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.Employee;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class TimeCardService {

	@Inject
	TimeCardDao t_dao;

	@SuppressWarnings("deprecation")
	public Time ComputeTime(int start_hour, int start_min) {
		Time tempo = new Time(start_hour, start_min, 0);
		return tempo;
	}

	@SuppressWarnings("deprecation")
	public String registerTimeCard(ContractorEmployee logged_employee,
			TimeCard timecard) {

		// check if for the given employee the timecard is
		// already sent for the specific date
		List<TimeCard> timecards = t_dao.findAll();

		for (TimeCard t : timecards) {

			// duble check
			// 1. if the time card t belongs to the logged employee
			// 2. if the time card we want to insert refers to a date not yet
			// inserted
			if (t.getContractor_employee().getId() == logged_employee.getId()) {

				// here i encounter a timecard with the same date already sent
				// from
				// the logged employee
				if (t.getDate().getMonth() == timecard.getDate().getMonth()
						&& t.getDate().getYear() == timecard.getDate()
								.getYear()
						&& t.getDate().getDay() == timecard.getDate().getDay()) {

					return "failed";
				}
			}
		}
		
		// if there is no time card sent by the logged employee
		// in a specific date, now we persist it to the db
		t_dao.add(timecard);

		return "success";
	}

	public List<TimeCard> retriveByEmployee(Employee e) {
		
		List<TimeCard> timecards = t_dao.findAll();
		List<TimeCard> retrieved = new ArrayList<TimeCard>();

		for (TimeCard t : timecards) {
			if (t.getContractor_employee().getId() == e.getId()) {
				retrieved.add(t);
			}
		}
		
		return retrieved;
	}

}
