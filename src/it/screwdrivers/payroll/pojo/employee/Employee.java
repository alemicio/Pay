package it.screwdrivers.payroll.pojo.employee;

import it.screwdrivers.payroll.pojo.historical.HistoricalSalary;
import it.screwdrivers.payroll.pojo.historical.HistoricalUnionCharge;
import it.screwdrivers.payroll.pojo.payment.Paymethod;
import it.screwdrivers.payroll.pojo.union.Union;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;

	private String username;
	private String password;
	private String name;
	private String surname;
	private String postal_address;
	private String e_mail;
	private String phone_number;

	@OneToOne
	@JoinColumn(name = "payment_id")
	private Paymethod paymethod;

	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "union_id")
	private Union union;

	@OneToMany(mappedBy="employee")
	private List<HistoricalSalary> historical_salaries;
	
	@OneToMany(mappedBy="employee")
	private List<HistoricalUnionCharge> historical_union_charge;
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPostal_address() {
		return postal_address;
	}

	public void setPostal_address(String postal_address) {
		this.postal_address = postal_address;
	}

	public String getE_mail() {
		return e_mail;
	}

	public void setE_mail(String e_mail) {
		this.e_mail = e_mail;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public int getId() {
		return employeeId;
	}


	public Paymethod getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(Paymethod paymethod) {
		this.paymethod = paymethod;
	}

	public Union getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public List<HistoricalSalary> getHistorical_salaries() {
		return historical_salaries;
	}

	public void setHistorical_salaries(List<HistoricalSalary> historical_salaries) {
		this.historical_salaries = historical_salaries;
	}

	public List<HistoricalUnionCharge> getHistorical_union_charge() {
		return historical_union_charge;
	}

	public void setHistorical_union_charge(
			List<HistoricalUnionCharge> historical_union_charge) {
		this.historical_union_charge = historical_union_charge;
	}

}
