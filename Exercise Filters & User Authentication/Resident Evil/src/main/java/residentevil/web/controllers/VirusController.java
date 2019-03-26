package residentevil.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil.entities.enums.Magnitude;
import residentevil.entities.enums.Mutation;
import residentevil.models.bindings.VirusCreateBindingModel;
import residentevil.models.services.CapitalServiceModel;
import residentevil.models.services.VirusServiceModel;
import residentevil.models.views.VirusesViewModel;
import residentevil.services.CapitalService;
import residentevil.services.VirusService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {
	
	private final VirusService virusService;
	private final CapitalService capitalService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public VirusController(VirusService virusService, CapitalService capitalService, ModelMapper modelMapper) {
		this.virusService = virusService;
		this.capitalService = capitalService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/add")
	public ModelAndView addVirus(ModelAndView modelAndView,
	                             @ModelAttribute("virusCreate") VirusCreateBindingModel virusCreate) {
		virusCreate.setCapitals(getCapitals());
		modelAndView.addObject("virusCreate", virusCreate);
		return this.view("addVirus", modelAndView);
	}
	
	@PostMapping("/add")
	public ModelAndView addVirusConfirm(ModelAndView modelAndView,
	                                    @Valid @ModelAttribute("virusCreate") VirusCreateBindingModel virusCreate,
	                                    BindingResult bindingResult) {
		if (bindingResult.hasErrors() || this.virusService.saveVirus(getVirusServiceModel(virusCreate)) == null) {
			virusCreate.setCapitals(getCapitals());
			modelAndView.addObject("virusCreate", virusCreate);
			return this.view("addVirus", modelAndView);
		}
		return this.redirect("/viruses/all");
	}
	
	@GetMapping("/all")
	public ModelAndView allViruses(ModelAndView modelAndView) {
		List<VirusesViewModel> virusesViewModels = this.virusService.getAllViruses().stream()
				.map(virus -> this.modelMapper.map(virus, VirusesViewModel.class))
				.collect(Collectors.toUnmodifiableList());
		modelAndView.addObject("allViruses", virusesViewModels);
		return this.view("allViruses", modelAndView);
	}
	
	@GetMapping("/edit")
	public ModelAndView editViruses(ModelAndView modelAndView,
	                                @RequestParam("id") String id,
	                                @ModelAttribute("virusCreate") VirusCreateBindingModel virusCreate) {
		virusCreate = this.modelMapper.map(this.virusService.getVirusById(id), VirusCreateBindingModel.class);
		virusCreate.setCapitals(getCapitals());
		modelAndView.addObject("virusCreate", virusCreate);
		return this.view("editVirus", modelAndView);
	}
	
	@PostMapping("/edit")
	public ModelAndView editVirusConfirm(ModelAndView modelAndView,
	                                     @RequestParam("id") String id,
	                                     @Valid @ModelAttribute("virusCreate") VirusCreateBindingModel virusCreate,
	                                     BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			virusCreate.setCapitals(getCapitals());
			modelAndView.addObject("virusCreate", virusCreate);
			return this.view("editVirus", modelAndView);
		}
		VirusServiceModel toSave = getVirusServiceModel(virusCreate);
		toSave.setId(id);
		if (this.virusService.saveVirus(toSave) == null) {
			virusCreate.setCapitals(getCapitals());
			modelAndView.addObject("virusCreate", virusCreate);
			return this.view("editVirus", modelAndView);
		}
		return this.redirect("/viruses/all");
	}
	
	@GetMapping("/delete")
	public ModelAndView deleteVirus(@RequestParam("id") String id) {
		this.virusService.deleteVirus(id);
		return this.redirect("/viruses/all");
	}
	
	private VirusServiceModel getVirusServiceModel(VirusCreateBindingModel virusCreate) {
		VirusServiceModel virusServiceModel = this.modelMapper.map(virusCreate, VirusServiceModel.class);
		virusServiceModel.setCapitals(new HashSet<>());
		virusCreate.getCapitals().stream()
				.map(this.capitalService::getCapitalByName)
				.forEach(capital -> virusServiceModel.getCapitals().add(capital));
		virusServiceModel.setMagnitude(Magnitude.valueOf(virusCreate.getMagnitude().toUpperCase()));
		virusServiceModel.setMutation(Mutation.valueOf(virusCreate.getMutation().toUpperCase()));
		return virusServiceModel;
	}
	
	private List<String> getCapitals() {
		return this.capitalService.getAllCapitals().stream()
				.map(CapitalServiceModel::getName)
				.collect(Collectors.toUnmodifiableList());
	}
}
