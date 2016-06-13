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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.domain.Runner;

public class AuthenticationServiceTest
{
    private AuthenticationService service;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Donor donor;

    @Mock
    private Runner runner;
    
    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        when(donor.getEmail()).thenReturn("blah@blah.blah");
        when(donor.getId()).thenReturn(111l);
        when(donor.getName()).thenReturn("bob cat");

        when(runner.getId()).thenReturn("222");
        
        service = new AuthenticationService();
        service.setSignatureKey("1234");
    }

    @Test
    public void testGetLoggedInDonor()
    {
        Cookie cookie = new Cookie("DONOR", "111 donor name");
        when(request.getCookies()).thenReturn(new Cookie[] { cookie });

        Donor donor = service.getLoggedInDonor(request);

        assertEquals("donor name", donor.getName());
        assertEquals((Long) 111l, donor.getId());
    }

    @Test
    public void testGetLoggedInDonorNoCookies()
    {
        when(request.getCookies()).thenReturn(null);
        Donor donor = service.getLoggedInDonor(request);
        assertNull(donor);
    }

    @Test
    public void testGetLoggedInDonorEmptyCookies()
    {
        when(request.getCookies()).thenReturn(new Cookie[0]);
        Donor donor = service.getLoggedInDonor(request);
        assertNull(donor);
    }

    @Test
    public void testCreateDonorCookie() throws Exception
    {
        Cookie cookie = service.createLoginCookie(donor);
        assertEquals("DONOR", cookie.getName());
        assertEquals("111 bob cat", cookie.getValue());
    }
    
    @Test
    public void testCreateRunnerCookie() throws Exception
    {
        Cookie cookie = service.createLoginCookie(runner);
        assertEquals("RUNNER", cookie.getName());
        assertTrue(cookie.getValue().startsWith("222|"));
    }
}
