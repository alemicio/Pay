package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.TimeCardService;
import it.screwdrivers.payroll.model.card.TimeCard;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("timecard")
@SessionScoped
public class TimeCardController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	TimeCardService time_card_service;

	//@Inject
	//TimeCard time_card;

	private int hour_start;
	private int hour_end;
	private int minute_start;
	private int minute_end;

	private Date date;
	private Time start_time;
	private Time end_time;
	private float hours_worked;

	public void submitTimeCard(ContractorEmployee logged_employee) {
		
		// Compute Time object given int inputs
		start_time = time_card_service.ComputeTime(hour_start, minute_start);
		end_time = time_card_service.ComputeTime(hour_end, minute_end);
		
		hours_worked = time_card_service.computeHoursWorked(start_time, end_time);

		TimeCard time_card = new TimeCard();
		time_card.setDate(date);
		time_card.setStart_time(start_time);
		time_card.setEnd_time(end_time);
		time_card.setHours_worked(hours_worked);
		time_card.setContractor_employee(logged_employee);

		String response = time_card_service.registerTimeCard(logged_employee, time_card);

		if (response == "failed") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Error sending Time Card",
					"A time card with this date was already sent"));
		}
		if (response == "success") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Congratulations",
					"Your time card was correctly sent"));
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getStart_time() {
		return start_time;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

	public int getHour_start() {
		return hour_start;
	}

	public void setHour_start(int hour_start) {
		this.hour_start = hour_start;
	}

	public int getHour_end() {
		return hour_end;
	}

	public void setHour_end(int hour_end) {
		this.hour_end = hour_end;
	}

	public int getMinute_start() {
		return minute_start;
	}

	public void setMinute_start(int minute_start) {
		this.minute_start = minute_start;
	}

	public int getMinute_end() {
		return minute_end;
	}

	public void setMinute_end(int minute_end) {
		this.minute_end = minute_end;
	}

	public float getHours_worked() {
		return hours_worked;
	}

	public void setHours_worked(float hours_worked) {
		this.hours_worked = hours_worked;
	}
}
