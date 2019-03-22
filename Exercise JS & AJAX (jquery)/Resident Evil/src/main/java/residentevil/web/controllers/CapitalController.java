package residentevil.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CapitalController extends BaseController {

    @GetMapping("/capital")
    public ModelAndView capital() {
        return this.view("index");
    }
}
