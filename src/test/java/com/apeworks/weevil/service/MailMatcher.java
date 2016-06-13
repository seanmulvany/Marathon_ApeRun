/*******************************************************************************
 * Copyright (C) 2012 apeworks
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 * 
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.apeworks.weevil.service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.mockito.ArgumentMatcher;

public class MailMatcher extends ArgumentMatcher<MimeMessage>
{
    private String fromName;

    private String fromAddress;

    private String donorEmail;

    private String donorName;

    private String subject;

    private String textContent;

    private String htmlContent;

    public MailMatcher(String fromName, String fromAddress, String donorName, String donorEmail, String subject, String textContent, String htmlContent)
    {
        this.fromName = fromName;
        this.fromAddress = fromAddress;
        this.donorName = donorName;
        this.donorEmail = donorEmail;
        this.subject = subject;
        this.textContent = textContent;
        this.htmlContent = htmlContent;
    }

    @Override
    public boolean matches(Object argument)
    {
        try
        {
            MimeMessage msg = (MimeMessage) argument;
            MimeMultipart content = (MimeMultipart) msg.getContent();

            return ((InternetAddress) msg.getFrom()[0]).getPersonal().equals(fromName) &&
                    ((InternetAddress) msg.getFrom()[0]).getAddress().equals(fromAddress) &&
                    ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getAddress().equals(donorEmail) &&
                    ((InternetAddress) msg.getRecipients(RecipientType.TO)[0]).getPersonal().equals(donorName) &&
                    msg.getSubject().equals(subject) &&
                    content.getContentType().startsWith("multipart/alternative") &&
                    content.getBodyPart(0).getContentType().equals("text/plain") &&
                    content.getBodyPart(0).getContent().equals(textContent) &&
                    // Poo. Doesn't seem to matter what's set here, it gets set to plain. 
                    // But all of that seems to be rewritten by appengine mail anyway. 
                    // content.getBodyPart(1).getContentType().equals("text/html") &&
                    content.getBodyPart(1).getContentType().equals("text/plain") &&
                    content.getBodyPart(1).getContent().equals(htmlContent);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
