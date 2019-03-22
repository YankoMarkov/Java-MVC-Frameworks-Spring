package residentevil.services;

import residentevil.models.services.VirusServiceModel;

import java.util.List;

public interface VirusService {

    VirusServiceModel saveVirus(VirusServiceModel virusService);

    List<VirusServiceModel> getAllViruses();

    VirusServiceModel getVirusById(String id);

    boolean deleteVirus(String id);
}
