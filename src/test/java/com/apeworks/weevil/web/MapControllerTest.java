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

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import com.apeworks.weevil.domain.Map;
import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.service.GameService;
import com.apeworks.weevil.service.MapService;
import com.apeworks.weevil.service.RunnerService;
import com.apeworks.weevil.transcoder.Transcoder;

public class MapControllerTest
{
    private MapController controller;

    @Mock
    private MapService mapService;

    @Mock
    private GameService gameService;
    
    @Mock
    private HttpServletRequest request;

    @Mock
    private ModelMap modelMap;

    @Mock
    private Runner runner;

    @Mock
    private Transcoder<Runner> runnerTranscoder;

    @Mock
    private ServletInputStream inputStream;

    @Mock
    private Map map;

    @Mock
    private DateUtil dateUtil;

    @Mock
    private Runner runnerAttribute;

    @Mock
    private RunnerService runnerService;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        controller = new MapController();
        controller.setMapService(mapService);
        controller.setRunnerTranscoder(runnerTranscoder);
        controller.setDateUtil(dateUtil);
        controller.setRunnerService(runnerService);
        controller.setGameService(gameService);

        when(request.getAttribute("LOGGED_IN_RUNNER")).thenReturn(runnerAttribute);
        when(mapService.getMap(null)).thenReturn(map);
        when(request.getInputStream()).thenReturn(inputStream);
        when(runnerTranscoder.decode(inputStream)).thenReturn(runner);
        when(runnerService.getRunner("123")).thenReturn(runner);
        when(runnerAttribute.getId()).thenReturn("123");
    }

    @Test
    public void testAddRunner() throws EncodingException, IOException
    {
        String view = controller.addRunner(request, modelMap);
        assertEquals("runner", view);

        verify(modelMap).put("runner", runner);
        verify(runner).setPin(null);
    }

    @Test
    public void testUpdate() throws EncodingException, IOException
    {
        String view = controller.update(null, request, modelMap);
        assertEquals("map", view);

        verify(mapService).updateRunner(runner);
        verify(mapService).getMap(null);
        verify(modelMap).put("map", map);
    }

    @Test
    public void testGet() throws EncodingException, IOException
    {
        String view = controller.getMap(null, modelMap);
        assertEquals("map", view);

        verify(mapService).getMap(null);
        verify(modelMap).put("map", map);
    }
}
