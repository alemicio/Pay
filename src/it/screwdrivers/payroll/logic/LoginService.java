package it.screwdrivers.payroll.logic;

import javax.ejb.Stateless;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Stateless
public class LoginService {
	
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public HttpSession getHttpSession(){
		HttpSession http_session = (HttpSession) getExternalContext().getSession(false);
		return http_session;
	}
	
	public void routeToIndex() {
		NavigationHandler nh = getFacesContext().getApplication().getNavigationHandler();
		nh.handleNavigation(getFacesContext(), null, "/xhtml/index.xhtml");
	}
}
