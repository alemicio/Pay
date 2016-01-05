package it.screwdrivers.payroll.model.union;

import it.screwdrivers.payroll.model.employee.Employee;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Union")
public class Union implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String phone_number;
	private String name;
	private float union_dues;
	
	// Since a Union may be associated to one or more Employee,
	// this attribute must be a List
	@OneToMany(mappedBy = "union")
	private List<Employee> employees;
	
	@OneToMany(mappedBy="union")
	private List<UnionServiceAssociation> union_service_associations;
	
	public String getPhone_number() {
		return phone_number;
	}
	
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public float getUnion_dues() {
		return union_dues;
	}
	
	public void setUnion_dues(float union_dues) {
		this.union_dues = union_dues;
	}
	
}
