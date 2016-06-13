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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Location;
import com.apeworks.weevil.service.DonationRepository;

//@Controller
public class PopulateDonations
{
    @Autowired
    private DonationRepository donationRepository;

    @RequestMapping(value = "/populateDonations", method = RequestMethod.GET)
    public String insertDonations()
    {
        for (int i = 0; i < 1000; i++)
            donationRepository.insert(createDonation());

        return "map";
    }

    private static Donation createDonation()
    {
        // -6.2118115 53.26772
        return new Donation(1, new Location(-6.21 + Math.random() * .02, 53.25 + Math.random() * .02));
    }
}
