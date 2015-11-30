package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.EmployeeController;
import it.screwdrivers.payroll.pojo.employee.Employee;

import java.io.Serializable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.sun.tools.classfile.Annotation.element_value;

@Named("login")
@SessionScoped
public class LogBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject 
	EmployeeController e_controller;
	
	private String username;
	private String password;

	private Employee retrived_employee;

	
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

	public void performLogin(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		retrived_employee = e_controller.checkLogin(username, password);
		
		if (retrived_employee == null) {
			context.addMessage(null, new FacesMessage("ERROR", "Account non presente !!!"));
		}
		else {
			context.addMessage(null, new FacesMessage("Benvenuto" + username));	
		}
		
		System.out.println(retrived_employee.getName());
		System.out.println(retrived_employee.getSurname());

    }

}
