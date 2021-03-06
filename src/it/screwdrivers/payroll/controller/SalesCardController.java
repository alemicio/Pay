package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.SalesCardService;
import it.screwdrivers.payroll.model.card.SalesCard;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("salescard")
@SessionScoped
public class SalesCardController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	SalesCardService sales_card_service;

	private Date date;
	private float amount;
	private String customer;

	public void submitSalesCard(CommissionedEmployee commissioned_employee) {
		
		SalesCard sales_card = new SalesCard();
		sales_card.setDate(date);
		sales_card.setAmount(amount);
		sales_card.setCustomer(customer);
		sales_card.setCommissioned_employee(commissioned_employee);

		String response = sales_card_service.registerTimeCard(
				commissioned_employee, sales_card);

		if (response == "success") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Congratulations",
					"your SalesCard was correctly sent"));
		}
		if (response == "failed") {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERROR",
					"A sales card from that customer was already"
							+ " sent for this date"));
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}
}
