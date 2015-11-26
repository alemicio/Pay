package it.screwdrivers.payroll.bean;

import java.awt.event.ActionEvent;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class LogBean implements Serializable {

	private static final long serialVersionUID = 1L;


    public void doPressButton(){
        System.out.println("dio porco");
    }

}
