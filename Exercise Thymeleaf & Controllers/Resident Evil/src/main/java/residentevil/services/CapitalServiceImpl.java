package residentevil.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil.entities.Capital;
import residentevil.models.services.CapitalServiceModel;
import residentevil.repositories.CapitalRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CapitalServiceImpl implements CapitalService {

    private final CapitalRepository capitalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CapitalServiceModel> getAllCapitals() {
        List<Capital> capitals = this.capitalRepository.findAllOrderByName();
        if (capitals == null) {
            return new ArrayList<>();
        }
        return capitals.stream()
                .map(capital -> this.modelMapper.map(capital, CapitalServiceModel.class))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public CapitalServiceModel getCapitalByName(String name) {
        Capital capital = this.capitalRepository.findByName(name).orElse(null);
        if (capital == null) {
            return null;
        }
        return this.modelMapper.map(capital, CapitalServiceModel.class);
    }
}
