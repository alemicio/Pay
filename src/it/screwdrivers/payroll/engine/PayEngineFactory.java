package it.screwdrivers.payroll.engine;

public class PayEngineFactory {

	public IPayEngine getPayEngine(String engineType) {

		if (engineType == null) {
			return null;
		}
		if (engineType == "Commissioned") {
			return new CommissionedPayEngine();

		} else if (engineType == "Contractor") {
			return new ContractorPayEngine();

		} else if (engineType == "Salaried") {
			return new SalariedPayEngine();
			
		} else if (engineType == "Manager") {
			return new ManagerPayEngine();
		}
		
		return null;

	}

}
