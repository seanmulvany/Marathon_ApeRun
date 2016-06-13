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
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import com.apeworks.weevil.domain.Location;
import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.service.PinGenerator;
import com.apeworks.weevil.service.RunnerService;

public class RunnerControllerTest
{
    private RunnerController controller;
    private BindingResult bindingResult;
    private RunnerService runnerService;
    private PinGenerator pinGenerator;
    private Runner runner;
    private ModelMap map;
    private List<Runner> runners;
    @Mock
    private Runner storedRunner;
    
    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        pinGenerator = mock(PinGenerator.class);
        when(pinGenerator.generate()).thenReturn("pin1");
        
        runners = Collections.emptyList();
        runnerService = mock(RunnerService.class);

        bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);

        controller = new RunnerController();
        controller.setPinGenerator(pinGenerator);
        controller.setRunnerService(runnerService);

        runner = mock(Runner.class);
        when(runner.getId()).thenReturn("id1");
        map = new ModelMap();
    }
    
    @Test
    public void testDoesntExist() throws Exception
    {
        assertEquals("redirect:/weevil/runnerAdmin.jsp", controller.createRunner(mock(HttpServletResponse.class), runner, bindingResult, map));
        
        Runner newRunner = new Runner("id1", new Location(50.0, 50.0), 100, 1000, 1000, 10000);
        newRunner.setPin("pin1");
        verify(runnerService).addRunner(newRunner);
        assertEquals(0, map.size());
    }
    
    @Test
    public void testExists() throws Exception
    {
        when(runnerService.getRunner("id1")).thenReturn(storedRunner);
        assertEquals("redirect:/weevil/runnerAdmin.jsp", controller.createRunner(mock(HttpServletResponse.class), runner, bindingResult, map));
        
        verify(storedRunner).setLocation(new Location(50.0, 50.0));
        verify(storedRunner).setPin("pin1");
        verify(storedRunner, never()).setTotal(anyInt());
        verify(runnerService).addRunner(storedRunner);
        assertEquals(0, map.size());
    }
    
    @Test
    public void testBindingErrors() throws Exception
    {
        when(bindingResult.hasErrors()).thenReturn(true);
        assertEquals("runnerAdmin", controller.createRunner(mock(HttpServletResponse.class), runner, bindingResult, map));        
        assertEquals(runner, map.get("runnerCredentials"));
        assertEquals(runners, map.get("runners"));
    }
}
