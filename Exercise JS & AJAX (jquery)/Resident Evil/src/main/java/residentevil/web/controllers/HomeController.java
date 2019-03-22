package residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import residentevil.models.views.CitiesAllViewModel;
import residentevil.models.views.VirusesAllViewModel;
import residentevil.services.CapitalService;
import residentevil.services.VirusService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final VirusService virusService;
    private final CapitalService capitalService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(VirusService virusService, CapitalService capitalService, ModelMapper modelMapper) {
        this.virusService = virusService;
        this.capitalService = capitalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ModelAndView index() {
        return this.view("index");
    }

    @GetMapping(value = "/viruses", produces = "application/json")
    @ResponseBody
    public Object virusesData() {
        List<VirusesAllViewModel> virusesAllViewModels = this.virusService.getAllViruses().stream()
                .map(virus -> this.modelMapper.map(virus, VirusesAllViewModel.class))
                .collect(Collectors.toUnmodifiableList());
        return virusesAllViewModels;
    }

    @GetMapping(value = "/cities", produces = "application/json")
    @ResponseBody
    public Object citiesData() {
        List<CitiesAllViewModel> citiesAllViewModels = this.capitalService.getAllCapitals().stream()
                .map(virus -> this.modelMapper.map(virus, CitiesAllViewModel.class))
                .collect(Collectors.toUnmodifiableList());
        return citiesAllViewModels;
    }
}
