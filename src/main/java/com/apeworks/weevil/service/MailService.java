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

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.stringtemplate.v4.ST;

import com.apeworks.weevil.domain.Donor;

@Service
public class MailService
{
    @Autowired
    private DonorService donorService;

    @Autowired
    private MailTransportService mailTransportService;

    @Value("${registration.text}")
    private String registrationText;

    @Value("${registration.html}")
    private String registrationHtml;

    @Value("${start.text}")
    private String startText;

    @Value("${start.html}")
    private String startHtml;

    @Value("${end.nothing.placed.text}")
    private String endNothingPlacedText;

    @Value("${end.nothing.placed.html}")
    private String endNothingPlacedHtml;

    @Value("${end.nothing.collected.text}")
    private String endNothingCollectedText;

    @Value("${end.nothing.collected.html}")
    private String endNothingCollectedHtml;

    @Value("${end.everything.collected.text}")
    private String endEverythingCollectedText;

    @Value("${end.everything.collected.html}")
    private String endEverythingCollectedHtml;

    @Value("${end.some.collected.text}")
    private String endSomeCollectedText;

    @Value("${end.some.collected.html}")
    private String endSomeCollectedHtml;

    @Value("${from.address}")
    private String fromAddress;

    @Value("${from.name}")
    private String fromName;

    @Value("${registration.subject}")
    private String registrationSubject;

    @Value("${start.subject}")
    private String startSubject;

    @Value("${end.subject}")
    private String endSubject;

    private static final Logger LOG = Logger.getLogger(MailService.class.getName());

    public void setDonorService(DonorService donorService)
    {
        this.donorService = donorService;
    }

    public void setMailTransportService(MailTransportService mailTransportService)
    {
        this.mailTransportService = mailTransportService;
    }

    public void sendRegistrationConfirmation(Donor donor)
    {
        sendMessage(donor, registrationSubject, populateTemplate(registrationText, donor), populateTemplate(registrationHtml, donor));
    }

    private String populateTemplate(String template, Donor donor)
    {
        ST st = new ST(template, '%', '%');
        st.add("donor", donor);
        st.add("totalPlaced", donor.getTotalCollected() + donor.getTotalUncollected());
        return st.render();
    }

    private void sendMessage(Donor donor, String subject, String textBody, String htmlBody)
    {
        try
        {
            sendMessageSafely(donor, subject, textBody, htmlBody);
            LOG.fine("Sent mail with subject [" + subject + "] to [" + donor.getEmail() + "]");
        }
        catch (Exception e)
        {
            LOG.warning("Failed to send mail with subject [" + subject + "] to [" + donor.getEmail() + "] - " + e.getMessage());
        }
    }

    private void sendMessageSafely(Donor donor, String subject, String textBody, String htmlBody) throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage msg = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
        msg.setFrom(new InternetAddress(fromAddress, fromName));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(donor.getEmail(), donor.getName(), "utf8"));
        msg.setSubject(subject);
        setBody(textBody, htmlBody, msg);
        mailTransportService.send(msg);
    }

    private void setBody(String textBody, String htmlBody, MimeMessage msg) throws MessagingException
    {
        MimeMultipart mimeMultipart = new MimeMultipart("alternative");
        addBodyPart(mimeMultipart, textBody, "plain");
        addBodyPart(mimeMultipart, htmlBody, "html");        
        msg.setContent(mimeMultipart);
    }

    private void addBodyPart(MimeMultipart mimeMultipart, String text, String subType) throws MessagingException
    {
        MimeBodyPart textBodyPart = new MimeBodyPart();
//        textBodyPart.setText(text, "utf8", subType);
        textBodyPart.setContent(text, "text/" + subType);
        mimeMultipart.addBodyPart(textBodyPart);
    }

    public void sendStartEventMail()
    {
        Collection<Donor> donors = donorService.getDonors();
        for (Donor donor : donors)
        {
            sendMessage(donor, startSubject, populateTemplate(startText, donor), populateTemplate(startHtml, donor));
        }
    }

    public void sendEndMail()
    {
        Collection<Donor> donors = donorService.getDonors();
        for (Donor donor : donors)
        {
            sendMessage(donor, endSubject, getEndText(donor), getEndHtml(donor));
        }
    }

    private String getEndText(Donor donor)
    {
        if (donor.getTotalUncollected() == 0 && donor.getTotalCollected() == 0)
            return populateTemplate(endNothingPlacedText, donor);
        if (donor.getTotalUncollected() > 0 && donor.getTotalCollected() == 0)
            return populateTemplate(endNothingCollectedText, donor);
        if (donor.getTotalUncollected() == 0 && donor.getTotalCollected() > 0)
            return populateTemplate(endEverythingCollectedText, donor);
        if (donor.getTotalUncollected() > 0 && donor.getTotalCollected() > 0)
            return populateTemplate(endSomeCollectedText, donor);
        return null;
    }

    private String getEndHtml(Donor donor)
    {
        if (donor.getTotalUncollected() == 0 && donor.getTotalCollected() == 0)
            return populateTemplate(endNothingPlacedHtml, donor);
        if (donor.getTotalUncollected() > 0 && donor.getTotalCollected() == 0)
            return populateTemplate(endNothingCollectedHtml, donor);
        if (donor.getTotalUncollected() == 0 && donor.getTotalCollected() > 0)
            return populateTemplate(endEverythingCollectedHtml, donor);
        if (donor.getTotalUncollected() > 0 && donor.getTotalCollected() > 0)
            return populateTemplate(endSomeCollectedHtml, donor);
        return null;
    }
}
