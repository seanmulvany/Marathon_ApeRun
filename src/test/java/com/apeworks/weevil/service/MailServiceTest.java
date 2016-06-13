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

import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Arrays;

import javax.mail.MessagingException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Donor;

public class MailServiceTest
{
    private MailService service;

    @Mock
    private Donor donor;

    @Mock
    private DonorService donorService;

    @Mock
    private MailTransportService transport;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        when(donor.getEmail()).thenReturn("a@example.com");
        when(donor.getName()).thenReturn("donorName");
        when(donor.getPin()).thenReturn("pin1");

        service = new MailService();
        service.setMailTransportService(transport);

        when(donorService.getDonors()).thenReturn(Arrays.asList(donor));

        service.setDonorService(donorService);
        setTextFields();
    }

    private void setTextFields() throws NoSuchFieldException, IllegalAccessException
    {
        setField("registrationText", "registration.text, %donor.name%, %donor.pin%");
        setField("registrationHtml", "registration.html, %donor.name%, %donor.pin%");
        setField("startText", "start.text, %donor.name%");
        setField("startHtml", "start.html, %donor.name%");
        setField("endNothingPlacedText", "end.nothing.placed.text, %donor.name%");
        setField("endNothingPlacedHtml", "end.nothing.placed.html, %donor.name%");
        setField("endNothingCollectedText", "end.nothing.collected.text, %donor.name%, %totalPlaced%");
        setField("endNothingCollectedHtml", "end.nothing.collected.html, %donor.name%, %totalPlaced%");
        setField("endEverythingCollectedText", "end.everything.collected.text, %donor.name%, %totalPlaced%");
        setField("endEverythingCollectedHtml", "end.everything.collected.html, %donor.name%, %totalPlaced%");
        setField("endSomeCollectedText", "end.some.collected.text, %donor.name%, %totalPlaced%, %donor.totalCollected%");
        setField("endSomeCollectedHtml", "end.some.collected.html, %donor.name%, %totalPlaced%, %donor.totalCollected%");
        setField("fromAddress", "from.address");
        setField("fromName", "from.name");
        setField("registrationSubject", "registration.subject");
        setField("startSubject", "start.subject");
        setField("endSubject", "end.subject");
    }

    @Test
    public void testSendRegistration() throws MessagingException
    {
        service.sendRegistrationConfirmation(donor);

        verify(transport).send(argThat(new MailMatcher(
                "from.name",
                "from.address",
                "donorName",
                "a@example.com",
                "registration.subject",
                "registration.text, donorName, pin1",
                "registration.html, donorName, pin1")));
    }

    @Test
    public void testSendStartMail() throws Exception
    {
        service.sendStartEventMail();

        verify(transport).send(argThat(new MailMatcher(
                "from.name",
                "from.address",
                "donorName",
                "a@example.com",
                "start.subject",
                "start.text, donorName",
                "start.html, donorName")));
    }

    @Test
    public void testSendEndMailNothingPlaced() throws Exception
    {
        when(donor.getTotalCollected()).thenReturn(0);
        when(donor.getTotalUncollected()).thenReturn(0);

        service.sendEndMail();

        verify(transport).send(argThat(new MailMatcher(
                "from.name",
                "from.address",
                "donorName",
                "a@example.com",
                "end.subject",
                "end.nothing.placed.text, donorName",
                "end.nothing.placed.html, donorName")));
    }

    @Test
    public void testSendEndMailNothingCollected() throws Exception
    {
        when(donor.getTotalCollected()).thenReturn(0);
        when(donor.getTotalUncollected()).thenReturn(1);

        service.sendEndMail();

        verify(transport).send(argThat(new MailMatcher(
                "from.name",
                "from.address",
                "donorName",
                "a@example.com",
                "end.subject",
                "end.nothing.collected.text, donorName, 1",
                "end.nothing.collected.html, donorName, 1")));
    }

    @Test
    public void testSendEndMailEverythingCollected() throws Exception
    {
        when(donor.getTotalCollected()).thenReturn(1);
        when(donor.getTotalUncollected()).thenReturn(0);

        service.sendEndMail();

        verify(transport).send(argThat(new MailMatcher(
                "from.name",
                "from.address",
                "donorName",
                "a@example.com",
                "end.subject",
                "end.everything.collected.text, donorName, 1",
                "end.everything.collected.html, donorName, 1")));
    }

    @Test
    public void testSendEndMailSomeCollected() throws Exception
    {
        when(donor.getTotalCollected()).thenReturn(1);
        when(donor.getTotalUncollected()).thenReturn(1);

        service.sendEndMail();

        verify(transport).send(argThat(new MailMatcher(
                "from.name",
                "from.address",
                "donorName",
                "a@example.com",
                "end.subject",
                "end.some.collected.text, donorName, 2, 1",
                "end.some.collected.html, donorName, 2, 1")));
    }

    private void setField(String fieldName, String fieldValue) throws NoSuchFieldException, IllegalAccessException
    {
        Field declaredField = service.getClass().getDeclaredField(fieldName);
        declaredField.setAccessible(true);
        declaredField.set(service, fieldValue);
    }
}
