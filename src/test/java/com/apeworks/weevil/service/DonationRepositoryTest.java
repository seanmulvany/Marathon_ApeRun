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

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.domain.Runner;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

public class DonationRepositoryTest
{
    private DonationRepository repository;

    @Mock
    private ObjectifyFactory objectifyFactory;

    @Mock
    private Objectify objectify;

    @Mock
    private Query<Donation> query;

    private List<Donation> donations;

    @Mock
    private Runner runner;

    @Mock
    private Donor donor;

    @Before
    public void setUp() throws Exception
    {
        donations = Arrays.asList(new Donation(3, null), new Donation(4, null));

        MockitoAnnotations.initMocks(this);
        repository = new DonationRepository();
        repository.setObjectifyFactory(objectifyFactory);

        when(objectifyFactory.begin()).thenReturn(objectify);
        when(objectify.query(Donation.class)).thenReturn(query);
        when(query.filter("collectedBy", null)).thenReturn(query);
        when(query.filter("collectedBy !=", null)).thenReturn(query);
        when(query.order("updated")).thenReturn(query);
        when(query.list()).thenReturn(donations);

        when(runner.getId()).thenReturn("runnerId");
        when(donor.getId()).thenReturn(111l);
    }

    @Test
    public void testInsert()
    {
        Donation donation = new Donation();
        repository.insert(donation);

        verify(objectify).put(donation);
    }

    @Test
    public void testGetUncollected()
    {
        assertEquals(donations, repository.getUncollectedDonations());
    }

    @Test
    public void testUpdate()
    {
        Donation donation = new Donation();
        repository.update(donation);

        verify(objectify).put(donation);
    }

    @Test
    public void testGetTotalCollectedByRunner()
    {
        when(query.filter("collectedBy", "runnerId")).thenReturn(query);

        assertEquals(7, repository.getTotalCollected(runner));
    }

    @Test
    public void testGetTotalCollectedForDonor()
    {
        when(query.filter("donorId", new Long(111))).thenReturn(query);
        assertEquals(7, repository.getTotalCollected(donor));
        verify(query).filter("collectedBy !=", null);
    }

    @Test
    public void testGetTotalUncollectedForDonor()
    {
        when(query.filter("donorId", new Long(111))).thenReturn(query);
        assertEquals(7, repository.getTotalUncollected(donor));
        verify(query).filter("collectedBy", null);
    }
}
