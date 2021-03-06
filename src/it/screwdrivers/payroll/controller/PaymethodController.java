package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.PaymethodService;
import it.screwdrivers.payroll.model.employee.Employee;
import it.screwdrivers.payroll.model.payment.BankPaymethod;
import it.screwdrivers.payroll.model.payment.PostalPaymethod;
import it.screwdrivers.payroll.model.payment.WithDrawPaymethod;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("paymentmethod")
@SessionScoped
public class PaymethodController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	PaymethodService paymethod_service;

	private String IBAN;
	private String filial;
	private String residential_address;
	private String headquarter;

	public String getPaymethodType(Employee e) {
		String type;
		boolean is_paymethod_set = paymethod_service.isPaymethodSet(e);

		if (is_paymethod_set) {
			type = paymethod_service.getPaymethodType(e.getPaymethod());
			return type;
		} else {
			return "Not setted";
		}
	}

	public void setBankPaymethod(Employee e) {
		BankPaymethod bank_paymethod = new BankPaymethod();
		bank_paymethod.setIBAN(IBAN);
		bank_paymethod.setFilial(filial);
		paymethod_service.setBankPaymethod(e, bank_paymethod);
	}

	public void setPostalPaymethod(Employee e) {
		PostalPaymethod postal_paymethod = new PostalPaymethod();
		postal_paymethod.setRedidential_address(residential_address);
		paymethod_service.setPostalPaymethod(e, postal_paymethod);
	}

	public void setWithDrawPaymethod(Employee e) {
		WithDrawPaymethod withdraw_paymethod = new WithDrawPaymethod();
		withdraw_paymethod = new WithDrawPaymethod();
		withdraw_paymethod.setHeadquarter(headquarter);
		paymethod_service.setWithDrawPaymethod(e, withdraw_paymethod);
	}

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
}
