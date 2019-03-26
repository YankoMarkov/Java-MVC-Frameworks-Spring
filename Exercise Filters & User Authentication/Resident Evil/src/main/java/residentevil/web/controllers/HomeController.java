package residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import residentevil.models.views.CitiesViewModel;
import residentevil.models.views.VirusesViewModel;
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

    @GetMapping("/home")
    public ModelAndView home() {
        return this.view("home");
    }

    @GetMapping(value = "/viruses", produces = "application/json")
    @ResponseBody
    public Object virusesData() {
        List<VirusesViewModel> virusesViewModels = this.virusService.getAllViruses().stream()
                .map(virus -> this.modelMapper.map(virus, VirusesViewModel.class))
                .collect(Collectors.toUnmodifiableList());
        return virusesViewModels;
    }

    @GetMapping(value = "/cities", produces = "application/json")
    @ResponseBody
    public Object citiesData() {
        List<CitiesViewModel> citiesViewModels = this.capitalService.getAllCapitals().stream()
                .map(virus -> this.modelMapper.map(virus, CitiesViewModel.class))
                .collect(Collectors.toUnmodifiableList());
        return citiesViewModels;
    }
}
