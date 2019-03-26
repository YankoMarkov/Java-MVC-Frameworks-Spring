package residentevil.services;

import residentevil.models.services.CapitalServiceModel;

import java.util.List;

public interface CapitalService {
	
	List<CapitalServiceModel> getAllCapitals();
	
	CapitalServiceModel getCapitalByName(String name);
}
