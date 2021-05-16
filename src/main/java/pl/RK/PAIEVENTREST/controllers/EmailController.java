package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.RK.PAIEVENTREST.services.implementations.EmailServiceImp;

import javax.mail.MessagingException;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/mail")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class EmailController {

    EmailServiceImp emailService;

    @Autowired
    public EmailController(EmailServiceImp emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/standard/{to}/{sub}/{isHtmlContent}")
    public void sendMail(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email") String to
            , @PathVariable String sub
            , @RequestBody @Size(min = 1, max = 255) String text,
                         @PathVariable boolean isHtmlContent) throws MessagingException {
        emailService.sendMail(to, sub, text, isHtmlContent);
    }

    @GetMapping("/reset/{to}")
    public ResponseEntity<?> sendRestPassword(@PathVariable
                                              @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                                      String to) throws MessagingException {
        return ResponseEntity.ok(emailService.sendResetPassword(to));
    }

    @GetMapping("/confirmation/{to}")
    public void sendConfirmation(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                         String to) throws MessagingException {
        emailService.sendConfirmation(to);
    }

    @GetMapping("/delete/{to}")
    public void sendDelete(@PathVariable @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "incorrect email")
                                   String to) throws MessagingException {
        emailService.sendDelete(to);
    }

}
