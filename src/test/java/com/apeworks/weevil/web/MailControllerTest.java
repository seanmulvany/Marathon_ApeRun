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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.service.MailService;

public class MailControllerTest
{
    private MailController controller;

    @Mock
    private MailService mailService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        controller = new MailController();
        controller.setMailService(mailService);
    }

    @Test
    public void testSendStartMail()
    {
        controller.sendStartMail();
        verify(mailService).sendStartEventMail();
        verifyNoMoreInteractions(mailService);
    }

    @Test
    public void testSendEndMail()
    {
        controller.sendEndMail();
        verify(mailService).sendEndMail();
        verifyNoMoreInteractions(mailService);
    }
}
