package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import service.CalcService;

@Controller
public class IndexController {

    @Autowired
    private CalcService calcService;

    @RequestMapping(value = "/calc", method = RequestMethod.GET)
    @ResponseBody
    public Double async() {
        return  calcService.calculate();
    }

}
