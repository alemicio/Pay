package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.TimeCardController;
import it.screwdrivers.payroll.pojo.card.TimeCard;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;

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
public class TimeCardBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	TimeCardController t_controller;

	@Inject
	TimeCard t_card;

	private Date date;
	private String response;

	private int hour_start;
	private int hour_end;
	private int minute_start;
	private int minute_end;

	private Time start_time;
	private Time end_time;

	private float hours_worked;

	@SuppressWarnings("deprecation")
	public void submitTimeCard(ContractorEmployee logged_employee) {

		// conversion from type int to time value
		start_time = t_controller.ComputeTime(hour_start, minute_start);
		end_time = t_controller.ComputeTime(hour_end, minute_end);

		hours_worked = (end_time.getHours() + (float) ((float) end_time.getMinutes() / 60))
				     - (start_time.getHours() + (float) ((float) start_time.getMinutes() / 60));

		t_card.setDate(date);
		t_card.setStart_time(start_time);
		t_card.setEnd_time(end_time);
		t_card.setHours_worked(hours_worked);
		t_card.setContractor_employee(logged_employee);


		response = t_controller.registerTimeCard(logged_employee, t_card);

		if (response == "failed") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_WARN, "Error sending Time Card",
					"A time card with thid date is already sent"));
		}
		if (response == "success") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Congratulations",
					"your time card is correctly sent"));
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
