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

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Donor;

public class DonorServiceTest
{
    private DonorService service;

    @Mock
    private MailService mailService;

    @Mock
    private Donor donor;

    @Mock
    private PinGenerator pinGenerator;

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private Donor repositoryDonor;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        service = new DonorService();
        service.setMailService(mailService);
        service.setPinGenerator(pinGenerator);
        service.setDonorRepository(donorRepository);

        when(donor.getEmail()).thenReturn("email1");
        when(donor.getPin()).thenReturn("pin1");
    }

    @Test
    public void testRegister() throws EmailInUseException
    {
        service.register(donor);
        verify(donor).setPin(anyString());
        verify(donorRepository).insert(donor);
        verify(mailService).sendRegistrationConfirmation(donor);
    }

    @Test
    public void testLoginSuccess()
    {
        when(donorRepository.getByEmail("email1")).thenReturn(repositoryDonor);
        when(repositoryDonor.getPin()).thenReturn("pin1");

        Donor loggedInDonor = service.login(donor);

        assertEquals(repositoryDonor, loggedInDonor);
    }

    @Test
    public void testLoginFail()
    {
        when(donorRepository.getByEmail("email1")).thenReturn(repositoryDonor);
        when(repositoryDonor.getPin()).thenReturn("pinXXX");

        assertEquals(null, service.login(donor));
    }

    @Test
    public void testLoginNoPin()
    {
        when(donorRepository.getByEmail("email1")).thenReturn(repositoryDonor);
        when(repositoryDonor.getPin()).thenReturn("pin1");
        when(donor.getPin()).thenReturn(null);

        assertEquals(null, service.login(donor));
    }

    @Test
    public void testLoginNoMatch()
    {
        when(donorRepository.getByEmail("email1")).thenReturn(null);

        assertEquals(null, service.login(donor));
    }

    @Test
    public void testGetDonor()
    {
        when(donorRepository.get(111)).thenReturn(donor);

        assertEquals(donor, service.getDonor(111));
    }
}
