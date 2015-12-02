package it.screwdrivers.payroll.controller;

import java.sql.Time;
import java.util.Date;
import java.util.Timer;

import it.screwdrivers.payroll.dao.TimeCardDao;
import it.screwdrivers.payroll.pojo.card.TimeCard;

import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class TimeCardController {
	
	@Inject
	TimeCardDao t_dao;

	
	@SuppressWarnings("deprecation")
	public Time ComputeTime(int start_hour, int start_min){
		Time tempo = new Time(start_hour, start_min, 0);
		
		return tempo;
	}

	public String registerTimeCard(TimeCard t){
		
		t_dao.add(t);
		
		//TODO 
		
		return null;
		
	}
	
	
	
	
	
	
	
	
}
