package it.screwdrivers.payroll.bean;

import it.screwdrivers.payroll.controller.EmployeeController;
import it.screwdrivers.payroll.pojo.employee.CommissionedEmployee;
import it.screwdrivers.payroll.pojo.employee.ContractorEmployee;
import it.screwdrivers.payroll.pojo.employee.EmployeeManager;
import it.screwdrivers.payroll.pojo.employee.SalariedEmployee;

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
public class ManagerBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	EmployeeController e_controller;
	
	private List<SalariedEmployee> s_employees;
	private List<CommissionedEmployee> com_employees;
	private List<EmployeeManager> m_employees;
	private List<ContractorEmployee> con_employees;
	
	
	
	@PostConstruct
	public void init(){
		
		s_employees   = e_controller.getAllSalaried();
		com_employees = e_controller.getAllCommissioned();
		con_employees = e_controller.getAllContractors();
		m_employees   = e_controller.getAllManagers();
	}
	
	public void onSalariedRowEdit(RowEditEvent event) {
		
		//here i want to update the db
		int id_employee = ((SalariedEmployee) event.getObject()).getId();
		float monthly_salary = ((SalariedEmployee) event.getObject()).getMonthly_salary();
		
		//set new values from the face of manager
		e_controller.updateSalariedEmployeeMonthlySalary(id_employee, monthly_salary);
		
        FacesMessage msg = new FacesMessage("Monthly Salary updated for employee :", ((SalariedEmployee) event.getObject()).getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onSalariedRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled",((SalariedEmployee) event.getObject()).getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    public void onCommissionedRowEdit(RowEditEvent event) {
    	
    	//here i want to update the db
    	int id_employee = ((CommissionedEmployee) event.getObject()).getId();
    	float monthly_salary = ((CommissionedEmployee) event.getObject()).getMonthly_salary();
    	float sale_rate =  ((CommissionedEmployee) event.getObject()).getSale_rate();
    	
    	//set new values from the face of manager
    	e_controller.updateCommissionedEmployeeMonthlySalarySaleRate(id_employee, monthly_salary,sale_rate);
    	
        FacesMessage msg = new FacesMessage("Commissioned Employee Edited", ((CommissionedEmployee) event.getObject()).getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCommissionedRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled",((CommissionedEmployee) event.getObject()).getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onContractorRowEdit(RowEditEvent event) {
    	
    	int id_employee = ((ContractorEmployee) event.getObject()).getId();
		float hourly_rate = ((ContractorEmployee) event.getObject()).getHourly_rate();
		
		//set new values from the face of manager
    	e_controller.updateContractorEmployeeHourlyRate(id_employee,hourly_rate);
		
        FacesMessage msg = new FacesMessage("Contractor Employee Edited", ((ContractorEmployee) event.getObject()).getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onContractorRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled",((ContractorEmployee) event.getObject()).getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void deleteSalariedRow(SalariedEmployee salaried) {  
        
    	System.out.println("sto cancellando un employee salariato");
    	System.out.println(salaried.getUsername());
    }  
	
	
	
	
	
	
	public List<SalariedEmployee> getS_employees() {
		return s_employees;
	}
	public List<CommissionedEmployee> getCom_employees() {
		return com_employees;
	}
	public List<EmployeeManager> getM_employees() {
		return m_employees;
	}
	public List<ContractorEmployee> getCon_employees() {
		return con_employees;
	}
	
	
	
	
	
	
}
