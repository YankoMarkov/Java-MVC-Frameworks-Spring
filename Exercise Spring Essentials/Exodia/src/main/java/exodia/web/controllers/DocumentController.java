package exodia.web.controllers;

import exodia.domain.models.bindings.DocumentCreateBindingModel;
import exodia.domain.models.services.DocumentServiceModel;
import exodia.domain.models.services.UserServiceModel;
import exodia.domain.models.views.DocumentDetailsViewModel;
import exodia.services.DocumentService;
import exodia.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class DocumentController extends BaseController {
	
	private final DocumentService documentService;
	private final UserService userService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public DocumentController(DocumentService documentService, UserService userService, ModelMapper modelMapper) {
		this.documentService = documentService;
		this.userService = userService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/schedule")
	public ModelAndView schedule(HttpSession session) {
		if (session.getAttribute("username") == null) {
			return this.redirect("/login");
		}
		return this.view("schedule");
	}
	
	@PostMapping("/schedule")
	public ModelAndView scheduleConfirm(@ModelAttribute @Valid DocumentCreateBindingModel documentCreate,
	                                    BindingResult bindingResult,
	                                    RedirectAttributes redirectAttributes,
	                                    HttpSession session) {
		
		if (bindingResult.hasErrors() || this.documentService.documentExist(documentCreate.getTitle())) {
			
			redirectAttributes.addFlashAttribute("errors", bindingResult.getFieldErrors().stream()
					.map(error -> error.getDefaultMessage()).collect(Collectors.toList()));
			return this.redirect("/schedule");
		}
		DocumentServiceModel documentServiceModel = this.modelMapper.map(documentCreate, DocumentServiceModel.class);
		UserServiceModel userServiceModel = this.userService.getUserByUsername(session.getAttribute("username").toString());
		documentServiceModel.setUser(userServiceModel);
		documentServiceModel = this.documentService.saveDocument(documentServiceModel);
		return this.redirect("/details/" + documentServiceModel.getId());
	}
	
	@GetMapping("/details/{id}")
	public ModelAndView details(@PathVariable("id") String id,
	                            ModelAndView modelAndView,
	                            HttpSession session) {
		
		if (session.getAttribute("username") == null) {
			return this.redirect("/login");
		}
		DocumentServiceModel documentServiceModel = this.documentService.getDocumentById(id);
		modelAndView.addObject("documentDetails", this.modelMapper.map(documentServiceModel, DocumentDetailsViewModel.class));
		return this.view("details", modelAndView);
	}
	
	@GetMapping("/print/{id}")
	public ModelAndView print(@PathVariable String id,
	                          ModelAndView modelAndView,
	                          HttpSession session) {
		
		if (session.getAttribute("username") == null) {
			return this.redirect("/login");
		}
		DocumentServiceModel documentServiceModel = this.documentService.getDocumentById(id);
		modelAndView.addObject("documentPrint", this.modelMapper.map(documentServiceModel, DocumentDetailsViewModel.class));
		return this.view("print", modelAndView);
	}
	
	@PostMapping("/print/{id}")
	public ModelAndView printConfirm(@PathVariable String id) {
		
		if (this.documentService.deleteDocument(id)) {
			return this.redirect("/home");
		}
		return this.redirect("/print/" + id);
	}
}
