package it.screwdrivers.payroll.pojo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EmployeeManager")
@DiscriminatorValue("EM")
public class EmployeeManager extends Employee {

	private static final long serialVersionUID = 1L;


}
