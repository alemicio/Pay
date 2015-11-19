package it.screwdrivers.payroll.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ContractorEmployee")
@DiscriminatorValue("ConE")
public class ContractorEmployee extends Employee {

	private static final long serialVersionUID = 1L;
	
	private float hourly_rate; //see "BigDecimal"

	public float getHourly_rate() {
		return hourly_rate;
	}

	public void setHourly_rate(float hourly_rate) {
		this.hourly_rate = hourly_rate;
	}

	
	
	

}
