package pl.RK.PAIEVENTREST.services.interfaces;

import javax.mail.MessagingException;

public interface EmailServiceIF {



    public void sendConfirmation(String to) throws MessagingException;

    public void sendResetPassword(String to) throws MessagingException;

    public void sendDelete(String to)throws MessagingException;

    public void sendMail(String to ,String subject ,String text ,boolean isHtmlContent) throws MessagingException;

}
