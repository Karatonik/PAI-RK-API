package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.services.implementations.EmailServiceImp;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "http://localhost:3000")
@EnableSwagger2
public class EmailController {

    EmailServiceImp emailService;
    @Autowired
    public EmailController(EmailServiceImp emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/standard/{to}/{sub}/{isHtmlContent}")
    public void sendMail(@PathVariable String to ,@PathVariable String sub, @RequestBody String text ,@PathVariable boolean isHtmlContent) throws MessagingException{
        emailService.sendMail(to,sub,text,isHtmlContent);
    }

    @GetMapping("/reset/{to}")
    public void sendRestPassword(@PathVariable String to) throws MessagingException{
        emailService.sendResetPassword(to);
    }

    @GetMapping("/confirmation/{to}")
    public void sendConfirmation(@PathVariable String to) throws MessagingException{
        emailService.sendConfirmation(to);
    }

    @GetMapping("/delete/{to}")
    public  void sendDelete(@PathVariable String to) throws MessagingException{
        emailService.sendDelete(to);
    }

}
