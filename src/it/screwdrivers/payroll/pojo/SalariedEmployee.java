package it.screwdrivers.payroll.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "SalariedEmployee")
@DiscriminatorValue("SE")
public class SalariedEmployee extends Employee {

	private static final long serialVersionUID = 1L;
	
	private float monthly_salary; //see "BigDecimal"

	public float getMonthly_salary() {
		return monthly_salary;
	}

	public void setMonthly_salary(float monthly_salary) {
		this.monthly_salary = monthly_salary;
	}
	
	

}
