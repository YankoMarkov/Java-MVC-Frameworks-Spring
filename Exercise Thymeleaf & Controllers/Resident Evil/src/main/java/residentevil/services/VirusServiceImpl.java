package residentevil.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import residentevil.entities.Virus;
import residentevil.models.services.VirusServiceModel;
import residentevil.repositories.VirusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {

    private final VirusRepository virusRepository;
    private final ModelMapper modelMapper;

    public VirusServiceImpl(VirusRepository virusRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public VirusServiceModel saveVirus(VirusServiceModel virusService) {
        Virus virus = this.modelMapper.map(virusService, Virus.class);
        virus = this.virusRepository.saveAndFlush(virus);
        if (virus == null) {
            return null;
        }
        return this.modelMapper.map(virus, VirusServiceModel.class);
    }

    @Override
    public List<VirusServiceModel> getAllViruses() {
        List<Virus> viruses = this.virusRepository.findAll();
        if (viruses == null) {
            return new ArrayList<>();
        }
        return viruses.stream()
                .map(virus -> this.modelMapper.map(virus, VirusServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public VirusServiceModel getVirusById(String id) {
        Virus virus = this.virusRepository.findById(id).orElse(null);
        if (virus == null) {
            return null;
        }
        return this.modelMapper.map(virus, VirusServiceModel.class);
    }

    @Override
    public boolean deleteVirus(String id) {
        try {
            this.virusRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
