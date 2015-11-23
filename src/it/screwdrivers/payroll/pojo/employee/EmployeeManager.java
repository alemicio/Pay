package it.screwdrivers.payroll.pojo.employee;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EmployeeManager")
@DiscriminatorValue("EM")
public class EmployeeManager extends Employee {

	private static final long serialVersionUID = 1L;
	
	private float annual_rate;

	public float getAnnual_rate() {
		return annual_rate;
	}

	public void setAnnual_rate(float annual_rate) {
		this.annual_rate = annual_rate;
	}
}
