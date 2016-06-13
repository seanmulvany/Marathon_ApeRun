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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;
import org.springframework.ui.ModelMap;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.transcoder.Transcoder;

public class RunnerViewTest
{
    private RunnerView runnerView;

    @Mock
    private ApplicationContext context;

    @Mock
    private Transcoder<Runner> transcoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ModelMap modelMap;

    @Mock
    private Runner runner;

    @Mock
    private ServletOutputStream outputStream;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        when(context.getBean("runnerTranscoder")).thenReturn(transcoder);
        when(modelMap.get("runner")).thenReturn(runner);
        when(response.getOutputStream()).thenReturn(outputStream);

        runnerView = new RunnerView();
        runnerView.setApplicationContext(context);
    }

    @Test
    public void test() throws Exception
    {
        runnerView.render(modelMap, request, response);
        verify(transcoder).encode(runner, outputStream);
    }

    @Test
    public void testContentType() throws Exception
    {
        assertEquals("application/json", runnerView.getContentType());
    }
}
