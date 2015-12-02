package it.screwdrivers.payroll.bean;

import java.io.Serializable;

import it.screwdrivers.payroll.controller.PaymethodController;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
import it.screwdrivers.payroll.pojo.payment.Paymethod;
import it.screwdrivers.payroll.pojo.payment.PostalPaymethod;
import it.screwdrivers.payroll.pojo.payment.WithDrawPaymethod;

import javax.inject.Inject;

public class PaymethodBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	PaymethodController paymethod_controller;
	
	private Employee logged_employee;
	private Paymethod paymethod;
	private String type;
	private BankPaymethod bank_paymethod;
	private PostalPaymethod postal_paymethod;
	private WithDrawPaymethod withdraw_paymethod;
	
	
	public String checkPaymethod(){
		
		//this method return a paymethod for a given employee.
		paymethod = paymethod_controller.checkPaymethodForEmployee(logged_employee);
		
		if(paymethod != null){
			type = paymethod_controller.findType(paymethod);
			return type;
		}
		
		return null;
	}
	public void changePaymethod(String paymethod_type){
		
		if(paymethod_type.equals("Bank")){
			
			
			
		}
		if(paymethod_type.equals("Postal")){
			
		}
		if(paymethod_type.equals("WithDraw")){
			
		}
		
		
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
