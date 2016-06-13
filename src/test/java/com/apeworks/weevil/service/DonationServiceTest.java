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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Donor;

public class DonationServiceTest
{
    private DonationService service;

    @Mock
    private DonationRepository donationRepository;
    
    @Mock
    private CacheService cacheService;
    

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private Donation donation;

    @Mock
    private Donor donor;

    @Mock
    private Donor storedDonor;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        service = new DonationService();
        service.setDonationRepository(donationRepository);
        service.setDonorRepository(donorRepository);
        service.setCache(cacheService);

        when(donor.getId()).thenReturn(111l);
        when(donor.getEmail()).thenReturn("email1");
//        when(donationRepository.getTotalUncollected(storedDonor)).thenReturn(123);
        when(donorRepository.get(111)).thenReturn(storedDonor);
        when(storedDonor.getTotalUncollected()).thenReturn(4);
        when(donation.getAmount()).thenReturn(5);
    }

    @Test
    public void testCreate()
    {
        service.create(donation, donor);

        verify(donation).setDonorId(111);
        verify(donationRepository).insert(donation);
        verify(storedDonor).setTotalUncollected(9);
        verify(donorRepository).update(storedDonor);
        verify(cacheService).clear();
    }

}
