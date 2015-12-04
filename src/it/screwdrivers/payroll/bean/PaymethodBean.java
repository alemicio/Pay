package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.PaymethodController;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.payment.BankPaymethod;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("paymentmethod")
@SessionScoped
public class PaymethodBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PaymethodController paymethod_controller;
	
	@Inject
	BankPaymethod bank_paymethod;
	
	private String IBAN;
	private String filial;
	private String residential_address;
	private String headquarter;

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getFilial() {
		return filial;
	}

	public void setFilial(String filial) {
		this.filial = filial;
	}

	public String getResidential_address() {
		return residential_address;
	}

	
	public void setResidential_address(String residential_address) {
		this.residential_address = residential_address;
	}

	public String getHeadquarter() {
		return headquarter;
	}

	public void setHeadquarter(String headquarter) {
		this.headquarter = headquarter;
	}

	//MICIO
	public String getPaymethodType(Employee e) {
		// // This method returns a paymethod for a given employee.
		String type;
		boolean is_paymethod_set = paymethod_controller.isPaymethodSet(e);

		if (is_paymethod_set == true) {
			type = paymethod_controller.findType(e.getPaymethod());
			return type;
		} else {
			return "Not setted";
		}
	}

	//MICIO
	public void setBankPaymethod(Employee e) {

		bank_paymethod.setIBAN(IBAN);
		bank_paymethod.setFilial(filial);
		
		System.out.println(IBAN + filial);
		System.out.println("00000000000000000000000000000000000000000000000");

		paymethod_controller.setBankPaymethod(e, bank_paymethod);
	}

	// public void setPostalPaymethod(String residential_address){
	//
	// PostalPaymethod postal_paymethod = new PostalPaymethod();
	// postal_paymethod.setRedidential_address(residential_address);
	//
	// paymethod_controller.setPostalPaymethod(e_bean.getRetrived_employee(),
	// postal_paymethod);
	// }
	//
	// public void setWithDrawPaymethod(String headquarter){
	//
	// WithDrawPaymethod withdraw_paymethod = new WithDrawPaymethod();
	// withdraw_paymethod.setHeadquarter(headquarter);
	//
	// paymethod_controller.setWithDrawPaymethod(e_bean.getRetrived_employee(),
	// withdraw_paymethod);
	// }
}
