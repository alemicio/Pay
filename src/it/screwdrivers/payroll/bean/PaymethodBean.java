package it.screwdrivers.payroll.bean;

import java.io.Serializable;

import it.screwdrivers.payroll.controller.PaymethodController;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.Paymethod;

import javax.inject.Inject;

public class PaymethodBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	PaymethodController paymethod_controller;
	
	private Employee logged_employee;
	private Paymethod paymethod;
	
	
	public void changePaymethod(){
		//TODO method for changing the paymethod for the logged employee
	}
	
	public Employee getLogged_employee() {
		return logged_employee;
	}
	public void setLogged_employee(Employee logged_employee) {
		this.logged_employee = logged_employee;
	}
	public Paymethod getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(Paymethod paymethod) {
		this.paymethod = paymethod;
	}
	
}
