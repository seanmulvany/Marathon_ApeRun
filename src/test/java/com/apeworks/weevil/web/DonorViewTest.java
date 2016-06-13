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

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.transcoder.Transcoder;

public class DonorViewTest
{
    private DonorView donorView;

    @Mock
    private ApplicationContext context;

    @Mock
    private Transcoder<Donor> transcoder;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ModelMap modelMap;

    @Mock
    private Donor donor;

    @Mock
    private ServletOutputStream outputStream;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        when(context.getBean("donorTranscoder")).thenReturn(transcoder);
        when(modelMap.get("donor")).thenReturn(donor);
        when(response.getOutputStream()).thenReturn(outputStream);

        donorView = new DonorView();
        donorView.setApplicationContext(context);
    }

    @Test
    public void test() throws Exception
    {
        donorView.render(modelMap, request, response);
        verify(transcoder).encode(donor, outputStream);
    }

    @Test
    public void testContentType() throws Exception
    {
        assertEquals("application/json", donorView.getContentType());
    }
}
