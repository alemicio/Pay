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
	CalendarService calendar_service;
	
	@Inject
	TimeCardDao t_dao;

	@SuppressWarnings("deprecation")
	public Time ComputeTime(int start_hour, int start_min) {
		Time time = new Time(start_hour, start_min, 0);
		return time;
	}

	@SuppressWarnings("deprecation")
	public float computeHoursWorked(Time start_time, Time end_time) {
		float hours_worked = (end_time.getHours() + (float) ((float) end_time
				.getMinutes() / 60))
				- (start_time.getHours() + (float) ((float) start_time
						.getMinutes() / 60));

		return hours_worked;
	}

	@SuppressWarnings("deprecation")
	public String registerTimeCard(ContractorEmployee logged_employee,
			TimeCard timecard) {

		// If it is saturday or sunday, the time card cannot be registered
		if (!calendar_service.isSaturday() || !calendar_service.isSunday()) {

			// It checks whether another TimeCard was sent by the given employee
			// in the same day. In that case, the method will return a "failed"
			// string. Otherwise, the TimeCard will be added to the db and
			// the method will return a "success" string
			List<TimeCard> timecards = t_dao.findAll();
			for (TimeCard t : timecards) {
				if (t.getContractor_employee().getId() == logged_employee
						.getId()) {
					if (t.getDate().getMonth() == timecard.getDate().getMonth()
							&& t.getDate().getYear() == timecard.getDate()
									.getYear()
							&& t.getDate().getDay() == timecard.getDate()
									.getDay()) {

						return "failed";
					}
				}
			}

			t_dao.add(timecard);
			return "success";

		} else {
			return "failed";
		}
	}

	public List<TimeCard> getByEmployee(Employee e) {
		List<TimeCard> timecards = t_dao.findAll();
		List<TimeCard> retrieved = new ArrayList<TimeCard>();

		for (TimeCard t : timecards) {
			if (t.getContractor_employee().getId() == e.getId())
				retrieved.add(t);
		}

		return retrieved;
	}
}
