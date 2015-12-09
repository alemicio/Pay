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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HistoricalUnionCharge")
public class HistoricalUnionCharge implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="employeeId", referencedColumnName="employeeId")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "union_service_association_id", referencedColumnName = "id")
	private UnionServiceAssociation union_service_association;
	
	private Date date;
	
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
	
	public UnionServiceAssociation getUnion_service_association() {
		return union_service_association;
	}
	public void setUnion_service_association(
			UnionServiceAssociation union_service_association) {
		this.union_service_association = union_service_association;
	}
	public int getId() {
		return id;
	}
}
