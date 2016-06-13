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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apeworks.weevil.domain.Donor;

@Service
public class DonorService
{
    @Autowired
    private MailService mailService;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private PinGenerator pinGenerator;

    public void setMailService(MailService mailService)
    {
        this.mailService = mailService;
    }

    public void setPinGenerator(PinGenerator pinGenerator)
    {
        this.pinGenerator = pinGenerator;
    }

    public void setDonorRepository(DonorRepository donorRepository)
    {
        this.donorRepository = donorRepository;
    }

    public void register(Donor donor) throws EmailInUseException
    {
        donor.setPin(generatePin());
        saveDonor(donor);
        emailDonor(donor);
    }

    private void emailDonor(Donor donor)
    {
        mailService.sendRegistrationConfirmation(donor);
    }

    private void saveDonor(Donor donor) throws EmailInUseException
    {
        donorRepository.insert(donor);
    }

    private String generatePin()
    {
        return pinGenerator.generate();
    }

    public Donor login(Donor donorCredentials)
    {
        Donor donor = donorRepository.getByEmail(donorCredentials.getEmail());
        return donor != null && donor.getPin().equals(donorCredentials.getPin()) ? donor : null;
    }

    public Donor getDonor(long id)
    {
        return donorRepository.get(id);
    }

    public Collection<Donor> getDonors()
    {
        return donorRepository.getDonors();
    }
}
