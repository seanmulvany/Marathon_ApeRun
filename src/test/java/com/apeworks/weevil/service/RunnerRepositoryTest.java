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
package com.apeworks.weevil.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.exception.EncodingException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

public class RunnerRepositoryTest
{
    private RunnerRepository repository;

    @Mock
    private ObjectifyFactory objectifyFactory;

    @Mock
    private Objectify objectify;

    @Mock
    private Query<Runner> query;

    @Mock
    private List<Runner> runners;

    @Mock
    private Runner runner;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        repository = new RunnerRepository();
        repository.setObjectifyFactory(objectifyFactory);

        when(objectifyFactory.begin()).thenReturn(objectify);
        when(objectify.query(Runner.class)).thenReturn(query);
        when(query.list()).thenReturn(runners);
        when(objectify.get(Runner.class, "runnerId")).thenReturn(runner);
    }

    @Test
    public void testAddRunner() throws EncodingException, IOException
    {
        repository.insert(runner);
        verify(objectify).put(runner);
    }

    @Test
    public void testUpdateRunner() throws EncodingException, IOException
    {
        repository.update(runner);
        verify(objectify).put(runner);
    }

    @Test
    public void testGetList() throws EncodingException, IOException
    {
        assertEquals(runners, repository.getRunners());
    }

    @Test
    public void testGetRunner() throws EncodingException, IOException
    {
        assertEquals(runner, repository.get("runnerId"));
    }
}
