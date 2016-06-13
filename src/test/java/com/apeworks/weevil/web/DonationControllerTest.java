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
package com.apeworks.weevil.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.exception.NotLoggedInException;
import com.apeworks.weevil.service.DonationService;
import com.apeworks.weevil.service.GameService;
import com.apeworks.weevil.transcoder.Transcoder;

public class DonationControllerTest
{
    private DonationController controller;

    @Mock
    private DonationService donationService;

    @Mock
    private Transcoder<Donation> transcoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Donation donation;

    @Mock
    private Donor donor;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private GameService gameService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        controller = new DonationController();
        controller.setDonationService(donationService);
        controller.setDonationTranscoder(transcoder);
        controller.setAuthenticationService(authenticationService);
        controller.setGameService(gameService);
        
        when(gameService.afterEnd()).thenReturn(false);

        when(authenticationService.getLoggedInDonor(request)).thenReturn(donor);
    }

    @Test
    public void testCreate() throws EncodingException, IOException, URISyntaxException, NotLoggedInException
    {
        when(transcoder.decode((InputStream) anyObject())).thenReturn(donation);
        when(request.getRequestURL()).thenReturn(new StringBuffer("url"));
        when(donation.getId()).thenReturn(123l);

        ResponseEntity<Void> createDonation = controller.createDonation(request);

        assertEquals("url/123", createDonation.getHeaders().getLocation().toString());
        verify(donationService).create(donation, donor);
    }

    @Test(expected = NotLoggedInException.class)
    public void testCreateNotLoggedIn() throws EncodingException, IOException, URISyntaxException, NotLoggedInException
    {
        when(authenticationService.getLoggedInDonor(request)).thenReturn(null);
        when(transcoder.decode((InputStream) anyObject())).thenReturn(donation);

        controller.createDonation(request);
    }
}
