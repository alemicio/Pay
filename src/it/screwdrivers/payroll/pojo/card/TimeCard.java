package it.screwdrivers.payroll.pojo.card;

import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TimeCard")
public class TimeCard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private Date date;
	private Time start_time;
	private Time end_time;
	
	//to do 
	//We need to have a relationship with the Contractor employee
	@ManyToOne
	@JoinColumn(name="employeeId",referencedColumnName="employeeId")
	private ContractorEmployee contractor_employee;
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Time getStart_time() {
		return start_time;
	}
	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}
	public Time getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

}
