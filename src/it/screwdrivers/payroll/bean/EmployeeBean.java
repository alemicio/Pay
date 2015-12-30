package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.EmployeeController;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.Employee;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("employee")
@SessionScoped
public class EmployeeBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EmployeeController e_controller;
	
	@Inject
	ManagerBean m_bean;

	private Employee retrieved_employee;
	private Employee updated_employee;

	private String username;
	private String password;
	private String name;
	private String surname;
	private String email;
	private String phone_number;
	private String postal_address;
	private float monthly_salary;
	private float sale_rate;
	private float hourly_rate;

	@PostConstruct
	public void init() {

		username = null;
		password = null;
		name = null;
		surname = null;
		email = null;
		phone_number = null;
		postal_address = null;
		monthly_salary = 0;
		sale_rate = 0;
		hourly_rate = 0;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getPostal_address() {
		return postal_address;
	}

	public void setPostal_address(String postal_address) {
		this.postal_address = postal_address;
	}

	public float getMonthly_salary() {
		return monthly_salary;
	}

	public void setMonthly_salary(float monthly_salary) {
		this.monthly_salary = monthly_salary;
	}

	public float getSale_rate() {
		return sale_rate;
	}

	public void setSale_rate(float sale_rate) {
		this.sale_rate = sale_rate;
	}

	public float getHourly_rate() {
		return hourly_rate;
	}

	public void setHourly_rate(float hourly_rate) {
		this.hourly_rate = hourly_rate;
	}

	public Employee retrieveEmployee(String username, String password) {

		retrieved_employee = e_controller.retrieveEmployee(username, password);
		return retrieved_employee;
	}

	public Employee getRetrieved_employee() {
		return retrieved_employee;
	}

	public void setRetrieved_employee(Employee retrieved_employee) {
		this.retrieved_employee = retrieved_employee;
	}

	public void addSalariedEmployee() {

		SalariedEmployee se = new SalariedEmployee();

		se.setUsername(username);
		se.setPassword(password);
		se.setName(name);
		se.setSurname(surname);
		se.setE_mail(email);
		se.setPhone_number(phone_number);
		se.setPostal_address(postal_address);
		se.setMonthly_salary(monthly_salary);

		e_controller.addEmployee(se);
		resetAttributes();
		
		m_bean.updateSalariedEmployeeList();
	}

	public void addCommissionedEmployee() {

		CommissionedEmployee ce = new CommissionedEmployee();

		ce.setUsername(username);
		ce.setPassword(password);
		ce.setName(name);
		ce.setSurname(surname);
		ce.setE_mail(email);
		ce.setPhone_number(phone_number);
		ce.setPostal_address(postal_address);
		ce.setMonthly_salary(monthly_salary);
		ce.setSale_rate(sale_rate);

		e_controller.addEmployee(ce);
		resetAttributes();
		
		m_bean.updateCommissionedEmployeeList();
	}

	public void addContractorEmployee() {
		
		ContractorEmployee ce = new ContractorEmployee();

		ce.setUsername(username);
		ce.setPassword(password);
		ce.setName(name);
		ce.setSurname(surname);
		ce.setE_mail(email);
		ce.setPhone_number(phone_number);
		ce.setPostal_address(postal_address);
		ce.setHourly_rate(hourly_rate);
		
		e_controller.addEmployee(ce);
		resetAttributes();
		
		m_bean.updateContractorEmployeeList();
	}

	private void resetAttributes() {
		username = null;
		password = null;
		name = null;
		surname = null;
		email = null;
		phone_number = null;
		postal_address = null;
		monthly_salary = 0;
		sale_rate = 0;
		hourly_rate = 0;
	}
}
