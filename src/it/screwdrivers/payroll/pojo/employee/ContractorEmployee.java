package it.screwdrivers.payroll.pojo.employee;

import it.screwdrivers.payroll.pojo.card.TimeCard;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ContractorEmployee")
@DiscriminatorValue("ConE")
public class ContractorEmployee extends Employee {

	private static final long serialVersionUID = 1L;
	
	private float hourly_rate; //see "BigDecimal"
	
	@OneToMany(mappedBy="contractor_employee")
	private List<TimeCard> timecards;

	public float getHourly_rate() {
		return hourly_rate;
	}

	public void setHourly_rate(float hourly_rate) {
		this.hourly_rate = hourly_rate;
	}
}
