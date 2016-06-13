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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.exception.NotLoggedInException;
import com.apeworks.weevil.service.DonationService;
import com.apeworks.weevil.service.GameService;
import com.apeworks.weevil.transcoder.Transcoder;

@Controller
public class DonationController
{
    @Autowired
    private DonationService donationService;

    @Autowired
    private Transcoder<Donation> donationTranscoder;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GameService gameService;
    
    public void setDonationService(DonationService donationService)
    {
        this.donationService = donationService;
    }

    public void setDonationTranscoder(Transcoder<Donation> donationTranscoder)
    {
        this.donationTranscoder = donationTranscoder;
    }

    public void setAuthenticationService(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    public void setGameService(GameService gameService)
    {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/donation", method = RequestMethod.POST)
    public ResponseEntity<Void> createDonation(HttpServletRequest request) throws EncodingException, IOException, URISyntaxException, NotLoggedInException
    {
        if (gameService.afterEnd())
            return new ResponseEntity<Void>(HttpStatus.GONE);
        
        Donation donation = donationTranscoder.decode(request.getInputStream());
        donationService.create(donation, getLoggedInDonor(request));

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(request.getRequestURL().append("/").append(donation.getId()).toString()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    private Donor getLoggedInDonor(HttpServletRequest request) throws NotLoggedInException
    {
        Donor donor = authenticationService.getLoggedInDonor(request);
        if (donor == null)
            throw new NotLoggedInException();
        return donor;
    }
}
