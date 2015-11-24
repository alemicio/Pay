package it.screwdrivers.payroll.pojo.payment;

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
	
	public String getIBAN() {
		return redidential_address;
	}
	public void setIBAN(String iBAN) {
		redidential_address = iBAN;
	}
}
