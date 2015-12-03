package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.PaymethodController;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
import it.screwdrivers.payroll.pojo.payment.PostalPaymethod;
import it.screwdrivers.payroll.pojo.payment.WithDrawPaymethod;

import java.io.Serializable;

import javax.inject.Inject;

public class PaymethodBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Inject
	PaymethodController paymethod_controller;
	
	@Inject 
	EmployeeBean e_bean;
	
	public String getPaymethodType(){
		
		Employee current_session_employee = e_bean.getRetrived_employee();
		
		// This method returns a paymethod for a given employee.
		boolean is_paymethod_set = paymethod_controller.isPaymethodSet(current_session_employee);
		
		if(is_paymethod_set){
			String type = paymethod_controller.findType(current_session_employee.getPaymethod());
			return type;
			
		} else {
			return null;
		}
	}
	
	public void setBankPaymethod(String iban,String filial){
		
		BankPaymethod bank_paymethod = new BankPaymethod();
		bank_paymethod.setIBAN(iban);
		bank_paymethod.setFilial(filial);
		
		paymethod_controller.setBankPaymethod(e_bean.getRetrived_employee(), bank_paymethod);	
	}
	
	public void seyPostalPaymethod(String residential_address){
		
		PostalPaymethod postal_paymethod = new PostalPaymethod();
		postal_paymethod.setRedidential_address(residential_address);
		
		paymethod_controller.setPostalPaymethod(e_bean.getRetrived_employee(), postal_paymethod);
	}
	
	public void setWithDrawPaymethod(String headquarter){
		
		WithDrawPaymethod withdraw_paymethod = new WithDrawPaymethod();
		withdraw_paymethod.setHeadquarter(headquarter);
		
		paymethod_controller.setWithDrawPaymethod(e_bean.getRetrived_employee(), withdraw_paymethod);	
	}	
}
