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
	
	public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Salaried Employee Edited", ((SalariedEmployee) event.getObject()).getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled",((SalariedEmployee) event.getObject()).getSurname());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
     
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
            //TODO here ww will update the db 
        }
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
