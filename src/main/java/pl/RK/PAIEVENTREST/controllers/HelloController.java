package pl.RK.PAIEVENTREST.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
public class HelloController {



    @GetMapping("/")
    public String getHello(){
        return "Api projekt PAI | Authors :|API-Karaton|  |FRONT-ROMZI|  |SUPPORT-ZUZI|";
    }
}
