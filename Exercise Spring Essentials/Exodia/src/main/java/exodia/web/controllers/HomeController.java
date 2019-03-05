package exodia.web.controllers;

import exodia.domain.models.views.DocumentAllViewModel;
import exodia.services.DocumentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
	
	private final DocumentService documentService;
	private final ModelMapper modelMapper;
	
	@Autowired
	public HomeController(DocumentService documentService, ModelMapper modelMapper) {
		this.documentService = documentService;
		this.modelMapper = modelMapper;
	}
	
	@GetMapping("/")
	public ModelAndView index(HttpSession session) {
		if (session.getAttribute("username") != null) {
			return this.redirect("/home");
		}
		return this.view("index");
	}
	
	@GetMapping("/home")
	public ModelAndView home(ModelAndView modelAndView, HttpSession session) {
		if (session.getAttribute("username") == null) {
			return this.redirect("/login");
		}
		List<DocumentAllViewModel> documentAllViewModels = this.documentService.getAllDocuments().stream()
				.map(document -> this.modelMapper.map(document, DocumentAllViewModel.class))
				.collect(Collectors.toList());
		modelAndView.addObject("documents", documentAllViewModels);
		return this.view("home", modelAndView);
	}
}
