package it.screwdrivers.payroll.controller;

import it.screwdrivers.payroll.logic.EmployeeService;
import it.screwdrivers.payroll.logic.LoginService;
import it.screwdrivers.payroll.model.employee.Employee;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

@Named("login")
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	LoginService login_service;

	@Inject
	EmployeeService employee_service;

	// These two attributes are set in the login form
	// when the "login" button is pressed
	private String username;
	private String password;

	private Employee logged_employee;
	private String type;

	// This method is called when the "LOGIN" button is pressed.
	// It calls the getEmployeeByUsernameAndPassword method. If
	// the return object is null, a warning message will be showed
	// in the view; otherwise, the type String will be set with
	// the employee's class simple name and the user_id variable
	// will be added to the session map. The type string is used
	// to permit the routing to the right employee's view
	public String performLogin() {
		logged_employee = employee_service.getEmployeeByUsernameAndPassword(
				username, password);

		if (logged_employee == null) {
			login_service.getFacesContext().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Uncorrect username or password",
							"Invalid credentials"));
		} else {
			type = employee_service.getEmployeeClassSimpleName(logged_employee);

			// This line will set a session attribute that we will check before
			// pages rendering
			login_service.getExternalContext().getSessionMap()
					.put("user_id", logged_employee.getId());
		}

		return type;
	}

	// This method allows to invalidate the session and route to
	// the index view returning "Logout" string
	public String performLogout() {
		login_service.getExternalContext().invalidateSession();
		return "Logout";
	}

	// This method is called as pre-rendering method in each view.
	// It gets the session attribute user_id and, if it is null,
	// will redirect the user to the index page
	public void checkIfLogged() {

		Object current_user_id = login_service.getHttpSession().getAttribute(
				"user_id");

		// TODO: substitute the following code with a return
		// the returned string has to be managed by the
		// faces-config.xml
		if (current_user_id == null) {
			login_service.routeToIndex();
		}
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

	public Employee getLogged_employee() {
		return logged_employee;
	}

	public void setLogged_employee(Employee logged_employee) {
		this.logged_employee = logged_employee;
	}
}
