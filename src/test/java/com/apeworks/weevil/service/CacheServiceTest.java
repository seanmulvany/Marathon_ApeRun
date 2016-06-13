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
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.Date;

import javax.cache.Cache;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Runner;

public class CacheServiceTest
{
    private CacheService cacheService;

    @Mock
    private Cache cache;

    @Mock
    private Collection<Runner> runners;

    @Mock
    private Collection<Donation> donations;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);

        cacheService = new CacheService();
        cacheService.cache = cache;
    }

    @Test
    public void setRunners() throws Exception
    {
        cacheService.setRunners(runners);
        verify(cache).put("all_runners", runners);
        verifyNoMoreInteractions(cache);
    }

    @Test
    public void getRunners() throws Exception
    {
        when(cache.get("all_runners")).thenReturn(runners);
        assertEquals(runners, cacheService.getRunners());
    }

    @Test
    public void setDonations() throws Exception
    {
        cacheService.setDonations(donations);
        verify(cache).put("all_donations", donations);
        verifyNoMoreInteractions(cache);
    }

    @Test
    public void setDonationsDate() throws Exception
    {
        cacheService.setDonations(donations, new Date(123));
        verify(cache).put(new Long(123), donations);
        verifyNoMoreInteractions(cache);
    }

    @Test
    public void getDonations() throws Exception
    {
        when(cache.get("all_donations")).thenReturn(donations);
        assertEquals(donations, cacheService.getDonations());
    }

    @Test
    public void getDonationDate() throws Exception
    {
        when(cache.get(new Long(123))).thenReturn(donations);
        assertEquals(donations, cacheService.getDonations(new Date(123)));
    }

    @Test
    public void clear() throws Exception
    {
        cacheService.clear();
        verify(cache).clear();
        verifyNoMoreInteractions(cache);
    }

    @Test
    public void clearRunners() throws Exception
    {
        cacheService.clearRunners();
        verify(cache).remove("all_runners");
        verifyNoMoreInteractions(cache);
    }
}
