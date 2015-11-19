package it.screwdrivers.payroll.pojo;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="BankPaymethod")
@DiscriminatorValue("Bank")
public class BankPaymethod extends Paymethod implements Serializable {

	private static final long serialVersionUID = 1L;
	private String IBAN;
	private String filial;
	
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
	
	

}
