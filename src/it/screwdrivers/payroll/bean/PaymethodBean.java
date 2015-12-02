package it.screwdrivers.payroll.bean;

import java.io.Serializable;

import it.screwdrivers.payroll.controller.PaymethodController;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
import it.screwdrivers.payroll.pojo.payment.Paymethod;
import it.screwdrivers.payroll.pojo.payment.PostalPaymethod;
import it.screwdrivers.payroll.pojo.payment.WithDrawPaymethod;
import it.screwdrivers.payroll.testsDB.PaymentTest;

import javax.inject.Inject;

public class PaymethodBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	PaymethodController paymethod_controller;
	BankPaymethod bank_paymethod;
	PostalPaymethod postal_paymethod;
	WithDrawPaymethod withdraw_paymethod;
	
	private Employee logged_employee;
	private Paymethod paymethod;
	private String type;
	
	
	
	public String checkPaymethod(){
		
		//this method return a paymethod for a given employee.
		paymethod = paymethod_controller.checkPaymethodForEmployee(logged_employee);
		
		if(paymethod != null){
			type = paymethod_controller.findType(paymethod);
			return type;
		}
		
		return null;
	}
	public void addBankPaymethod(String iban,String filial){
		
		bank_paymethod.setIBAN(iban);
		bank_paymethod.setFilial(filial);
		
		paymethod_controller.addingNewBankPaymethod(logged_employee,bank_paymethod);	
	}
	
	public void addPostalPaymethod(String residential_address){
		
		postal_paymethod.setRedidential_address(residential_address);
		
		paymethod_controller.addingNewPostalPaymethod(logged_employee, postal_paymethod);	
	}
	
	public void addWithDrawPaymethod(String headquarter){
		
		withdraw_paymethod.setHeadquarter(headquarter);
		
		paymethod_controller.addingNewWithDrawPaymethod(logged_employee, withdraw_paymethod);	
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
