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
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.exception.EncodingException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

public class DonorRepositoryTest
{
    private DonorRepository repository;

    @Mock
    private ObjectifyFactory objectifyFactory;

    @Mock
    private Objectify objectify;

    @Mock
    private Query<Donor> query;

    @Mock
    private List<Donor> donors;

    @Mock
    private Donor donor;

    @Mock
    private Donor existingDonor;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        repository = new DonorRepository();
        repository.setObjectifyFactory(objectifyFactory);

        when(objectifyFactory.begin()).thenReturn(objectify);
        when(objectify.query(Donor.class)).thenReturn(query);
        when(objectify.get(Donor.class, 123)).thenReturn(donor);
        when(query.filter(eq("email"), anyString())).thenReturn(query);
        when(query.filter(eq("name"), anyString())).thenReturn(query);
        when(query.list()).thenReturn(donors);

        when(donor.getEmail()).thenReturn("email1");
        when(donor.getPin()).thenReturn("pin1");
    }

    @Test
    public void testAddDonor() throws EncodingException, IOException, EmailInUseException
    {
        repository.insert(donor);
        verify(objectify).put(donor);
    }

    @Test(expected = EmailInUseException.class)
    public void testAddDonorEmailCollision() throws EncodingException, IOException, EmailInUseException
    {
        Query<Donor> collisionQuery = mock(Query.class);

        when(query.filter(eq("email"), anyString())).thenReturn(collisionQuery);
        when(collisionQuery.get()).thenReturn(existingDonor);

        repository.insert(donor);
    }

    @Test
    public void testUpdateDonor() throws EncodingException, IOException
    {
        repository.update(donor);
        verify(objectify).put(donor);
    }

    @Test
    public void testGetList() throws EncodingException, IOException
    {
        assertEquals(donors, repository.getDonors());
    }

    @Test
    public void testGet() throws EncodingException, IOException
    {
        assertEquals(donor, repository.get(123));
    }

    @Test
    public void testGetNotFound() throws EncodingException, IOException
    {
        assertNull(repository.get(1234));
    }
}
