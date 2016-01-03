package it.screwdrivers.payroll.pojo.card;

import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
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
@Table(name="SalesCard")
public class SalesCard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private float amount;
	private Date date;
	
	private String customer;
	
	@ManyToOne
	@JoinColumn(name="employeeId",referencedColumnName="employeeId")
	private CommissionedEmployee commissioned_employee;
	
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
	
	public CommissionedEmployee getCommissioned_employee() {
		return commissioned_employee;
	}
	
	public void setCommissioned_employee(CommissionedEmployee commissioned_employee) {
		this.commissioned_employee = commissioned_employee;
	}
	
	public int getId() {
		return id;
	}
	
	public String getCustomer() {
		return customer;
	}
	
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	
}
