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

import com.apeworks.weevil.domain.Location;
import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.service.PinGenerator;
import com.apeworks.weevil.service.RunnerService;
import com.apeworks.weevil.domain.Run;
import com.apeworks.weevil.service.RunService;

@Controller
public class RunnerController
{
    private RunnerService runnerService;

    private RunService runService;

    private PinGenerator pinGenerator;
    
    @Autowired
    public void setRunnerService(RunnerService runnerService)
    {
        this.runnerService = runnerService;
    }

    @Autowired
    public void setRunService(RunService runService)
    {
        this.runService = runService;
    }

    @Autowired
    public void setPinGenerator(PinGenerator pinGenerator)
    {
        this.pinGenerator = pinGenerator;
    }

    @RequestMapping(value = "runnerAdmin", method = RequestMethod.GET)
    public String showForm(ModelMap modelMap)
    {
        Runner runner = new Runner();
        modelMap.put("runnerCredentials", runner);
        modelMap.put("runners", runnerService.getRunners());
        return "runnerAdmin";
    }

    @RequestMapping(value = "runnerAdmin", method = RequestMethod.POST)
    public String createRunner(HttpServletResponse response, @Valid @ModelAttribute("runnerCredentials") Runner runner, BindingResult bindingResult, ModelMap map) throws IOException
    {
        if (!bindingResult.hasErrors())
        {
            storeRunner(runner.getId(), bindingResult, response);
            return "redirect:/weevil/runnerAdmin.jsp";
        }
        else
        {
            map.put("runnerCredentials", runner);
            map.put("runners", runnerService.getRunners());
            return "runnerAdmin";
        }
    }

    private void storeRunner(String runnerId, BindingResult bindingResult, HttpServletResponse response)
    {
        Runner runner = getOrCreateRunner(runnerId);        
        runner.setLocation(new Location(50.0, 50.0));
        runner.setTime(System.currentTimeMillis());
        runner.setDistance(7);
        runner.setDuration(10);
        runner.setSpeed(11);
        runner.setPin(pinGenerator.generate());
        runnerService.addRunner(runner);

        //Run run = new Run ("abc", 10);
        //runService.create(run, runner);
    }

    private Runner getOrCreateRunner(String runnerId)
    {
        Runner runner = runnerService.getRunner(runnerId);
        if (runner == null)
            runner = new Runner(runnerId);
        return runner;
    }
}
