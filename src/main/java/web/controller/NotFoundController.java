package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NotFoundController implements ErrorController {

    @Autowired
    private PageController pageController;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView notFound() {
        return pageController.build("error", null);
    }
}
