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

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.service.DonorService;

@Controller
public class DonorController
{
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private DonorService donorService;

    public void setAuthenticationService(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    public void setDonorService(DonorService donorService)
    {
        this.donorService = donorService;
    }

    @RequestMapping(value = "/donor", method = RequestMethod.GET)
    public String getLoggedInDonor(HttpServletRequest request, ModelMap modelMap) throws EncodingException, IOException
    {
        Donor loggedInDonor = authenticationService.getLoggedInDonor(request);
        modelMap.put("donor", loggedInDonor != null ? donorService.getDonor(loggedInDonor.getId()) : null);
        return "donor";
    }
}
