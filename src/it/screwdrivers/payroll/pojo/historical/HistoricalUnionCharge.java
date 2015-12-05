package it.screwdrivers.payroll.pojo.historical;

import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.union.UnionServiceAssociation;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HistoricalUnionCharge")
public class HistoricalUnionCharge implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private float amount;
	private Date date;
	
	@ManyToOne
	@JoinColumn(name="employeeId", referencedColumnName="employeeId")
	private Employee employee;
	
	// In questa @ManyToOne ho dovuto mettere due JoinComlumn dato che
	// la tabella UnionServiceAssociation ha una CHIAVE PRIMARIA COMPOSTA
	@ManyToOne
	@JoinColumns({	
		@JoinColumn(name = "union_id", referencedColumnName = "union_id", insertable = false, updatable = false),             
	    @JoinColumn(name = "union_service_id", referencedColumnName = "union_service_id", insertable = true, updatable = true)
	})
	private UnionServiceAssociation union_service_association;
	
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
}
