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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.service.RunnerService;

@Controller
public class RunnerLoginController
{
    private RunnerService runnerService;

    private AuthenticationService authenticationService;

    @Autowired
    public void setAuthenticationService(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    @Autowired
    public void setRunnerService(RunnerService runnerService)
    {
        this.runnerService = runnerService;
    }

    @RequestMapping(value = "runnerLogin", method = RequestMethod.GET)
    public String showForm(ModelMap modelMap)
    {
        Runner runner = new Runner();

        modelMap.put("runnerCredentials", runner);
        return "runnerLogin";
    }

    @RequestMapping(value = "runnerLogin", method = RequestMethod.POST)
    public String login(HttpServletResponse response, @Valid @ModelAttribute("runnerCredentials") Runner runner, BindingResult bindingResult, ModelMap map) throws IOException
    {
        if (!bindingResult.hasErrors() && loginRunner(runner, bindingResult, response))
            return "redirect:/runner.html";
        else
        {
            map.put("runnerCredentials", runner);
            return "runnerLogin";
        }
    }

    private boolean loginRunner(Runner runner, BindingResult bindingResult, HttpServletResponse response)
    {
        Runner loggedInRunner = runnerService.login(runner);

        if (loggedInRunner != null)
        {
            response.addCookie(authenticationService.createLoginCookie(loggedInRunner));
            return true;
        }
        else
        {
            bindingResult.reject("invalid.credentials", "Incorrect email or password");
            return false;
        }
    }
}
