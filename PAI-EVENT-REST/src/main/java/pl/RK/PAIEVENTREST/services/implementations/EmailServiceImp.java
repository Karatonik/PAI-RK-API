package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.EmailServiceIF;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class EmailServiceImp implements EmailServiceIF {

    private JavaMailSender javaMailSender;

    private UserPaiRepository userPaiRepository;
    @Value("${my.confMessage}")
    private String confMessage;

    @Value("${my.resetMessage}")
    private String resetMessage;

    @Value("${my.deleteMessage}")
    private String deleteMessage;

    @Autowired
    public EmailServiceImp(JavaMailSender javaMailSender, UserPaiRepository userPaiRepository) {
        this.javaMailSender = javaMailSender;
        this.userPaiRepository = userPaiRepository;
    }


    @Override
    public void sendConfirmation(String to) throws MessagingException {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(to);
        if (optionalUserPAI.isPresent()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Confirmation");
            mimeMessageHelper.setText(confMessage + optionalUserPAI.get().getUserKey(), true);
            javaMailSender.send(mimeMessage);
        }
    }

    @Override
    public void sendResetPassword(String to) throws MessagingException {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(to);
        if (optionalUserPAI.isPresent()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Reset Password");
            mimeMessageHelper.setText(resetMessage + optionalUserPAI.get().getUserKey(), true);
            javaMailSender.send(mimeMessage);
        }
    }

    @Override
    public void sendDelete(String to) throws MessagingException {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(to);
        if (optionalUserPAI.isPresent()) {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("Delete Account");
            mimeMessageHelper.setText(deleteMessage + optionalUserPAI.get().getUserKey(), true);
            javaMailSender.send(mimeMessage);
        }
    }

    @Override
    public void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHtmlContent);
        javaMailSender.send(mimeMessage);
    }


}
