package pl.RK.PAIEVENTREST.services.interfaces;

import javax.mail.MessagingException;

public interface EmailServiceIF {


    void sendConfirmation(String to) throws MessagingException;

    boolean sendResetPassword(String to) throws MessagingException;

    void sendDelete(String to) throws MessagingException;

    void sendMail(String to, String subject, String text, boolean isHtmlContent) throws MessagingException;

}
