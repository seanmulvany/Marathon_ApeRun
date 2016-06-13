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
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.service.DonorService;
import com.apeworks.weevil.service.EmailInUseException;
import com.apeworks.weevil.service.GameService;

public class RegistrationControllerTest
{
    private RegistrationController controller;

    @Mock
    private DonorService donorService;

    @Mock
    private GameService gameService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Donor donor;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private Cookie cookie;

    private ModelMap modelMap;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        modelMap = new ModelMap();
        controller = new RegistrationController();
        controller.setDonorService(donorService);
        controller.setAuthenticationService(authenticationService);
        controller.setGameService(gameService);

        when(authenticationService.createLoginCookie(donor)).thenReturn(cookie);
    }

    @Test
    public void testGet() throws Exception
    {
        assertEquals("login", controller.showForm(modelMap));
        assertEquals(modelMap.get("donor"), new Donor());
        assertEquals(modelMap.get("donorCredentials"), new Donor());
        assertEquals(modelMap.get("registrationMessage"), "Not registered? Enter your email address below and we'll send you a PIN.");
        assertEquals(modelMap.get("registrationDisabledAttribute"), "");
    }

    @Test
    public void testGetAfterEvent() throws Exception
    {
        when(gameService.afterEnd()).thenReturn(true);
        
        assertEquals("login", controller.showForm(modelMap));
        assertEquals(modelMap.get("donor"), new Donor());
        assertEquals(modelMap.get("donorCredentials"), new Donor());
        assertEquals(modelMap.get("registrationMessage"), "Registration for this event has ended.");
        assertEquals(modelMap.get("registrationDisabledAttribute"), "disabled=\"disabled\"");
    }

    @Test
    public void testRegister() throws EncodingException, IOException, EmailInUseException
    {
        Donor donor = mock(Donor.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        String view = controller.register(donor, bindingResult, null, modelMap);
        assertEquals("login", view);

        verify(donorService).register(donor);
        verify(donor).setPin(null);
        assertEquals(modelMap.get("registrationMessage"), "Not registered? Enter your email address below and we'll send you a PIN.");
        assertEquals(modelMap.get("registrationDisabledAttribute"), "");
        assertEquals(modelMap.get("successMessage"), "Thank you! You will shortly receive an email with your login PIN.");
        assertEquals(modelMap.get("donorCredentials"), donor);
    }

    @Test
    public void testRegisterErrors() throws EncodingException, IOException, EmailInUseException
    {
        Donor donor = new Donor();
        when(bindingResult.hasErrors()).thenReturn(true);
        String view = controller.register(donor, bindingResult, null, modelMap);
        assertEquals("login", view);

        verify(donorService, never()).register(donor);
        assertEquals(modelMap.get("successMessage"), null);
        assertEquals(modelMap.get("donorCredentials"), new Donor());
    }
    
    @Test
    public void testRegisterIsKiosk() throws EncodingException, IOException, EmailInUseException
    {
        Donor donor = mock(Donor.class);
        assertEquals("login", controller.register(donor, bindingResult, "true", modelMap));

        verify(donorService).register(donor);
        assertEquals(modelMap.get("donorCredentials"), donor);
        verify(donor, never()).setPin(null);
        assertEquals(modelMap.get("successMessage"), "Thank you! You can now log in with the PIN shown. Your PIN has also been emailed to you.");
    }

    @Test
    public void testInUseErrors() throws EncodingException, IOException, EmailInUseException
    {
        Donor donor = new Donor();
        when(bindingResult.hasErrors()).thenReturn(false);
        doThrow(new EmailInUseException("email")).when(donorService).register(donor);

        String view = controller.register(donor, bindingResult, null, modelMap);
        assertEquals("login", view);

        assertEquals(modelMap.get("successMessage"), null);
        assertEquals(modelMap.get("donorCredentials"), new Donor());
        verify(bindingResult).rejectValue(Matchers.eq("email"), anyString(), anyString());
    }

    @Test
    public void testLoginSuccess() throws IOException
    {
        Donor donorCredentials = mock(Donor.class);
        when(donorService.login(donorCredentials)).thenReturn(donor);

        String view = controller.login(response, donorCredentials, bindingResult, modelMap);

        assertEquals("redirect:/donor.html", view);
        verify(response).addCookie(cookie);
    }

    @Test
    public void testLoginFail() throws IOException
    {
        when(donorService.login(donor)).thenReturn(null);

        String view = controller.login(response, donor, bindingResult, modelMap);

        assertEquals("login", view);
        verify(response, never()).addCookie(cookie);
        assertEquals(modelMap.get("donor"), donor);
        verify(bindingResult).reject(eq("invalid.credentials"), anyString());
    }

    @Test
    public void testLoginBindingError() throws IOException
    {
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = controller.login(response, donor, bindingResult, modelMap);

        assertEquals("login", view);
        verify(response, never()).addCookie(cookie);
        assertEquals(modelMap.get("donor"), donor);
    }
}
