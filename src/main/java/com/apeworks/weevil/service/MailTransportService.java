/**
 * 
 */
package com.apeworks.weevil.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import org.springframework.stereotype.Service;

@Service
public class MailTransportService
{
    public void send(Message message) throws MessagingException {
        Transport.send(message);
    }
}
