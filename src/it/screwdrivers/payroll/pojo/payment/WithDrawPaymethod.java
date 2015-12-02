package it.screwdrivers.payroll.pojo.payment;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="WithDrawPaymethod")
@DiscriminatorValue("headquarter")
public class WithDrawPaymethod extends Paymethod implements Serializable {

	private static final long serialVersionUID = 1L;
	private String headquarter;
	
	public String getHeadquarter() {
		return headquarter;
	}
	public void setHeadquarter(String headquarter) {
		this.headquarter = headquarter;
	}
	
	
	
}
