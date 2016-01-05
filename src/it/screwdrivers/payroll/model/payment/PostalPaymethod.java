package it.screwdrivers.payroll.model.payment;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PostalPaymethod")
@DiscriminatorValue("Postal")
public class PostalPaymethod extends Paymethod implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String redidential_address;
	
	public String getRedidential_address() {
		return redidential_address;
	}
	
	public void setRedidential_address(String redidential_address) {
		this.redidential_address = redidential_address;
	}
	
}
