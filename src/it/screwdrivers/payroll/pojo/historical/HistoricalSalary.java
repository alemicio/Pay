package it.screwdrivers.payroll.pojo.historical;

import it.screwdrivers.payroll.pojo.employee.Employee;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HistoricalSalary")
public class HistoricalSalary implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private float amount;
	private Date date;
	private boolean isCommission;
	
	@ManyToOne
	@JoinColumn(name="employeeId", referencedColumnName="employeeId")
	private Employee employee;
	
	public float getAmount() {
		return amount;
	}
	
	public void setAmount(float amount) {
		this.amount = amount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isCommission() {
		return isCommission;
	}
	
	public void setCommission(boolean isCommission) {
		this.isCommission = isCommission;
	}
	
}
