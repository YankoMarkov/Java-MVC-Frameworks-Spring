package realestate.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import realestate.domain.dtos.bindings.OfferFindDto;
import realestate.domain.dtos.bindings.OfferRegisterDto;
import realestate.domain.dtos.view.OfferViewDto;
import realestate.service.OfferService;
import realestate.util.FileUtil;

import java.io.IOException;
import java.util.List;

@Controller
public class OfferController {

    private final OfferService offerService;
    private final FileUtil fileUtil;

    @Autowired
    public OfferController(OfferService offerService, FileUtil fileUtil) {
        this.offerService = offerService;
        this.fileUtil = fileUtil;
    }

    @GetMapping("/reg")
    public String register() {
        return "register.html";
    }

    @PostMapping("/reg")
    public String registerConfirm(@ModelAttribute OfferRegisterDto offerRegisterDto) {
        try {
            this.offerService.saveOffer(offerRegisterDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "redirect:/reg";
        }
        return "redirect:/";
    }

    @GetMapping("/find")
    public String find() {
        return "find.html";
    }

    @PostMapping("/find")
    @ResponseBody
    public String findByRequirements(@ModelAttribute OfferFindDto offerFindDto) throws IOException {
        List<OfferViewDto> offerViewDtos;
        try {
            offerViewDtos = this.offerService.getAllOfferByApartmentTypeAndBudget(offerFindDto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "redirect:/find";
        }
        return prepareHtml(offerViewDtos);
    }

    private String prepareHtml(List<OfferViewDto> offerViewDtos) throws IOException {
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
        return this.fileUtil.readFile("src/main/resources/static/index.html")
                .replace("{{offers}}", result);
    }
}
