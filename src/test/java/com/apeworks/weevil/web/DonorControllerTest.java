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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.service.DonorService;

public class DonorControllerTest
{
    private DonorController controller;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ModelMap modelMap;

    @Mock
    private DonorService donorService;

    @Mock
    private Donor requestDonor;

    @Mock
    private Donor storedDonor;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        controller = new DonorController();
        controller.setAuthenticationService(authenticationService);
        controller.setDonorService(donorService);

        when(authenticationService.getLoggedInDonor(request)).thenReturn(requestDonor);
        when(requestDonor.getId()).thenReturn(111l);
        when(donorService.getDonor(111)).thenReturn(storedDonor);
    }

    @Test
    public void testGetLoggedInDonor() throws EncodingException, IOException
    {
        String view = controller.getLoggedInDonor(request, modelMap);
        assertEquals("donor", view);
        verify(modelMap).put("donor", storedDonor);
    }

    @Test
    public void testNotLoggedIn() throws EncodingException, IOException
    {
        when(authenticationService.getLoggedInDonor(request)).thenReturn(null);
        String view = controller.getLoggedInDonor(request, modelMap);
        assertEquals("donor", view);
        verify(modelMap).put("donor", null);
    }
}
