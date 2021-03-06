package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.EmployeeService;
import it.screwdrivers.payroll.logic.HistoricalSalaryService;
import it.screwdrivers.payroll.model.employee.CommissionedEmployee;
import it.screwdrivers.payroll.model.employee.ContractorEmployee;
import it.screwdrivers.payroll.model.employee.EmployeeManager;
import it.screwdrivers.payroll.model.employee.SalariedEmployee;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

@Named("manager")
@SessionScoped
public class ManagerController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	EmployeeService employee_service;

	@Inject
	HistoricalSalaryService historical_salary_service;

	// These are all the employees attributes. They will be initialized
	// to null (0 for the float attributes) and used as
	// temporary attributes during employee creation. Since
	// the employee is created, they will be reset
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
	
	public List<EmployeeManager> getAllManagers() {
		return employee_service.getAllManagers();
	}
	
	public List<SalariedEmployee> getAllSalaried() {
		return employee_service.getAllSalaried();
	}
	
	public List<CommissionedEmployee> getAllCommissioned() {
		return employee_service.getAllCommissioned();
	}
	
	public List<ContractorEmployee> getAllContractors() {
		return employee_service.getAllContractors();
	}

	public void onSalariedRowEdit(RowEditEvent event) {
		// here i want to update the db
		int id_employee = ((SalariedEmployee) event.getObject()).getId();
		float monthly_salary = ((SalariedEmployee) event.getObject())
				.getMonthly_salary();

		// set new values from the face of manager
		employee_service.updateSalariedEmployeeMonthlySalary(id_employee,
				monthly_salary);

		FacesMessage msg = new FacesMessage(
				"Monthly Salary updated for employee :",
				((SalariedEmployee) event.getObject()).getSurname());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onSalariedRowCancel(RowEditEvent event) {
		SalariedEmployee s = ((SalariedEmployee) event.getObject());
		int id_employee = s.getId();

		boolean response = employee_service.deleteEmployee(id_employee);

		if (response) {
			FacesMessage msg = new FacesMessage("Employee deleted: ",
					((SalariedEmployee) event.getObject()).getSurname());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage(
					"Error: impossible delete employee :",
					((SalariedEmployee) event.getObject()).getSurname());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onCommissionedRowEdit(RowEditEvent event) {
		// here i want to update the db
		int id_employee = ((CommissionedEmployee) event.getObject()).getId();
		float monthly_salary = ((CommissionedEmployee) event.getObject())
				.getMonthly_salary();
		float sale_rate = ((CommissionedEmployee) event.getObject())
				.getSale_rate();

		// set new values from the face of manager
		employee_service.updateCommissionedEmployeeMonthlySalarySaleRate(
				id_employee, monthly_salary, sale_rate);

		FacesMessage msg = new FacesMessage("Commissioned Employee Edited",
				((CommissionedEmployee) event.getObject()).getSurname());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCommissionedRowCancel(RowEditEvent event) {
		CommissionedEmployee c = ((CommissionedEmployee) event.getObject());
		int id_employee = c.getId();

		boolean response = employee_service.deleteEmployee(id_employee);

		if (response) {
			FacesMessage msg = new FacesMessage("Employee deleted: ",
					((CommissionedEmployee) event.getObject()).getSurname());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage(
					"Error: impossible delete employee :",
					((CommissionedEmployee) event.getObject()).getSurname());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onContractorRowEdit(RowEditEvent event) {
		int id_employee = ((ContractorEmployee) event.getObject()).getId();
		float hourly_rate = ((ContractorEmployee) event.getObject())
				.getHourly_rate();

		// set new values from the face of manager
		employee_service.updateContractorEmployeeHourlyRate(id_employee,
				hourly_rate);

		FacesMessage msg = new FacesMessage("Contractor Employee Edited",
				((ContractorEmployee) event.getObject()).getSurname());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onContractorRowCancel(RowEditEvent event) {
		ContractorEmployee c = ((ContractorEmployee) event.getObject());
		int id_employee = c.getId();

		boolean response = employee_service.deleteEmployee(id_employee);

		if (response) {
			FacesMessage msg = new FacesMessage("Employee deleted: ",
					((ContractorEmployee) event.getObject()).getSurname());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {
			FacesMessage msg = new FacesMessage("Error",
					" It was impossible to delete employee :"
							+ ((ContractorEmployee) event.getObject())
									.getSurname());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Cell Changed", "Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public boolean addSalariedEmployee() {
		boolean available;
		SalariedEmployee se = new SalariedEmployee();

		se.setUsername(username);
		se.setPassword(password);
		se.setName(name);
		se.setSurname(surname);
		se.setE_mail(email);
		se.setPhone_number(phone_number);
		se.setPostal_address(postal_address);
		se.setMonthly_salary(monthly_salary);
		available = employee_service.addEmployee(se);

		resetAttributes();

		return available;
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

		employee_service.addEmployee(ce);
		resetAttributes();
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

		employee_service.addEmployee(ce);
		resetAttributes();
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
}
