package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.EmployeeController;
import it.screwdrivers.payroll.controller.TimeCardController;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.annotation.ManagedBean;
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

	private Date date;
	
	private int hour_start;
	private int hour_end;
	private int minute_start;
	private int minute_end;
	
	private Time start_time;
	private Time end_time;
	
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
	
	


}
