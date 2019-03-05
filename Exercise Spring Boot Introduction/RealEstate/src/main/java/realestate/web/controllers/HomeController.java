package realestate.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import realestate.domain.dtos.view.OfferViewDto;
import realestate.service.OfferService;
import realestate.util.FileUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {
	
	private final OfferService offerService;
	private final ModelMapper modelMapper;
	private final FileUtil fileUtil;
	
	@Autowired
	public HomeController(OfferService offerService, ModelMapper modelMapper, FileUtil fileUtil) {
		this.offerService = offerService;
		this.modelMapper = modelMapper;
		this.fileUtil = fileUtil;
	}
	
	@GetMapping("/")
	@ResponseBody
	public String index() throws IOException {
		return this.fileUtil.readFile("src/main/resources/static/index.html")
				.replace("{{offers}}", prepareHtml());
	}
	
	private String prepareHtml() {
		List<OfferViewDto> offerViewDtos = this.offerService.findAllOffers();
		StringBuilder result = new StringBuilder();
		
		if (offerViewDtos.isEmpty()) {
			result.append("\t<div class=\"apartment\" style=\"border: red solid 1px\">").append(System.lineSeparator());
			result.append("<p>There aren't any offers!</p>").append(System.lineSeparator());
			result.append("\t</div>").append(System.lineSeparator());
		} else {
			for (OfferViewDto offerViewDto : offerViewDtos) {
				result.append("\t<div class=\"apartment\">").append(System.lineSeparator());
				result.append(String.format("<p>Rent: %s</p>", offerViewDto.getApartmentRent())).append(System.lineSeparator());
				result.append(String.format("<p>Type: %s</p>", offerViewDto.getApartmentType())).append(System.lineSeparator());
				result.append(String.format("<p>Commission: %s</p>", offerViewDto.getAgencyCommission())).append(System.lineSeparator());
				result.append("\t</div>").append(System.lineSeparator());
			}
		}
		return result.toString().trim();
	}
}
