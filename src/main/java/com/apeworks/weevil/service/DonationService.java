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

import java.util.Collections;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Donor;

@Service
public class DonationService
{
    @Autowired
    private CacheService cache;
    
    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonorRepository donorRepository;

    public void setDonationRepository(DonationRepository donationRepository)
    {
        this.donationRepository = donationRepository;
    }

    public void setDonorRepository(DonorRepository donorRepository)
    {
        this.donorRepository = donorRepository;
    }

    public void setCache(CacheService cache)
    {
        this.cache = cache;
    }

    public void create(Donation donation, Donor donor)
    {
        donation.setDonorId(donor.getId());
        donationRepository.insert(donation);
        cache.clear();
        updateDonor(donor, donation);
    }

    private void updateDonor(Donor donor, Donation donation)
    {
        Donor storedDonor = donorRepository.get(donor.getId());
        storedDonor.setTotalUncollected(storedDonor.getTotalUncollected() + donation.getAmount());
        donorRepository.update(storedDonor);
    }

    public void deleteAll()
    {
        donationRepository.deleteAll();
    }
}
