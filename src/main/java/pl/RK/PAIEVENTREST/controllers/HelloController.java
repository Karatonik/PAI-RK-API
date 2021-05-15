package pl.RK.PAIEVENTREST.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HelloController {



    @GetMapping("/")
    public String getHello(){
        return "Api projekt PAI /n Authors :API-Karaton , FRONT-ROMZI ,SUPPORT-ZUZI";
    }
}
