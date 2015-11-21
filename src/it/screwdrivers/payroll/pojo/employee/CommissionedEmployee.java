package it.screwdrivers.payroll.pojo.employee;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CommissionedEmployee")
@DiscriminatorValue("ComE")
public class CommissionedEmployee extends SalariedEmployee {

	private static final long serialVersionUID = 1L;
	
	private float sale_rate; //see "BigDecimal"

	public float getSale_rate() {
		return sale_rate;
	}

	public void setSale_rate(float sale_rate) {
		this.sale_rate = sale_rate;
	}


}
