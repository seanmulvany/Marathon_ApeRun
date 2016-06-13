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

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.service.DonorService;
import com.apeworks.weevil.service.EmailInUseException;
import com.apeworks.weevil.service.GameService;

@Controller
public class RegistrationController
{
    private DonorService donorService;

    private AuthenticationService authenticationService;

    private GameService gameService;

    @Autowired
    public void setDonorService(DonorService donorService)
    {
        this.donorService = donorService;
    }

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setGameService(GameService gameService)
    {
        this.gameService = gameService;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String showForm(ModelMap modelMap)
    {
        modelMap.put("donor", new Donor());
        modelMap.put("donorCredentials", new Donor());
        setDefaults(modelMap);
        return "login";
    }

    private void setDefaults(ModelMap modelMap)
    {
        modelMap.put("registrationMessage", gameService.afterEnd() ?
                "Registration for this event has ended." :
                "Not registered? Enter your email address below and we'll send you a PIN.");
        modelMap.put("registrationDisabledAttribute", gameService.afterEnd() ? "disabled=\"disabled\"" : "");
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletResponse response, @Valid @ModelAttribute("donorCredentials") Donor donor, BindingResult bindingResult, ModelMap map) throws IOException
    {
        if (!bindingResult.hasErrors() && loginDonor(donor, bindingResult, response))
            return "redirect:/donor.html";
        else
        {
            setDefaults(map);
            map.put("donor", donor);
            return "login";
        }
    }

    private boolean loginDonor(Donor donor, BindingResult bindingResult, HttpServletResponse response)
    {
        Donor loggedInDonor = donorService.login(donor);

        if (loggedInDonor != null)
        {
            response.addCookie(authenticationService.createLoginCookie(loggedInDonor));
            return true;
        }
        else
        {
            bindingResult.reject("invalid.credentials", "Incorrect email or password");
            return false;
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(
            @Valid @ModelAttribute("donor") Donor donor, 
            BindingResult bindingResult, 
            @CookieValue(value = "KIOSK", required = false) String kioskCookie, 
            ModelMap map) throws IOException
    {
        boolean isKiosk = kioskCookie != null;
        setDefaults(map);

        if (gameService.afterEnd())
            return "redirect:/donor.html";

        if (!bindingResult.hasErrors())
        {
            registerUser(donor, bindingResult, isKiosk, map);
            if (!isKiosk)
                donor.setPin(null);
            map.put("donorCredentials", donor);
        }
        else
        {
            map.put("donorCredentials", new Donor());
        }

        return "login";
    }

    private void registerUser(Donor donor, BindingResult bindingResult, boolean isKiosk, ModelMap map)
    {
        try
        {
            donorService.register(donor);
            map.put("successMessage", isKiosk ?
                    "Thank you! You can now log in with the PIN shown. Your PIN has also been emailed to you." :
                    "Thank you! You will shortly receive an email with your login PIN.");
        }
        catch (EmailInUseException e)
        {
            bindingResult.rejectValue("email", "email.in.use", "Oops, that email was used before. Check your mail for the PIN.");
        }
    }
}
