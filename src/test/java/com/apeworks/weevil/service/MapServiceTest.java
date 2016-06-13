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

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.domain.Location;
import com.apeworks.weevil.domain.Map;
import com.apeworks.weevil.domain.Runner;
import com.googlecode.objectify.NotFoundException;

public class MapServiceTest
{
    private MapService service;

    @Mock
    private RunnerRepository runnerRepository;

    @Mock
    private DonationRepository donationRepository;

    @Mock
    private DonorRepository donorRepository;

    @Mock
    private CacheService cacheService;

    @Mock
    private List<Runner> runners;

    @Mock
    private Runner runner;

    @Mock
    private Runner updateRunner;

    private Runner[] runnerArray = new Runner[] {};

    private Donation[] donationArray = new Donation[] {};

    private Collection<Donation> donations;

    @Mock
    private Donation donation1;

    @Mock
    private Donation donation2;

    @Mock
    private Donor donor;

    @Before
    public void setup() throws Exception
    {
        MockitoAnnotations.initMocks(this);
        service = new MapService();
        service.setRunnerRepository(runnerRepository);
        service.setDonationRepository(donationRepository);
        service.setDonorRepository(donorRepository);
        service.setCacheService(cacheService);

        when(runnerRepository.getRunners()).thenReturn(runners);
        when(runnerRepository.get("runnerId")).thenReturn(runner);
        when(runners.toArray(new Runner[0])).thenReturn(runnerArray);

        when(runner.getId()).thenReturn("runnerId");
        when(runner.getLocation()).thenReturn(new Location(-6.25996, 53.34434));
        when(runner.getTotal()).thenReturn(3);

        when(updateRunner.getId()).thenReturn("runnerId");
        when(updateRunner.getLocation()).thenReturn(new Location(-6.25996, 53.34434));

        createDonations();
        when(donationRepository.getUncollectedDonations()).thenReturn(donations);
        when(donationRepository.getDonations(new Date(123))).thenReturn(donations);
        when(donorRepository.get(111l)).thenReturn(donor);
        
        when(donor.getTotalCollected()).thenReturn(0);
        when(donor.getTotalUncollected()).thenReturn(1);
        
        when(cacheService.getDonations()).thenReturn(null);
        when(cacheService.getDonations((Date) any())).thenReturn(null);
        when(cacheService.getRunners()).thenReturn(null);
    }

    private void createDonations()
    {
        donations = new ArrayList<Donation>();

        when(donation1.getDonorId()).thenReturn(111l);
        when(donation1.getLocation()).thenReturn(new Location(-6.25998, 53.34450));
        when(donation1.getAmount()).thenReturn(1);
        donations.add(donation1);

        when(donation2.getDonorId()).thenReturn(222l);
        when(donation2.getLocation()).thenReturn(new Location(-6.26039, 53.34431));
        when(donation2.getAmount()).thenReturn(2);
        donations.add(donation2);

        donationArray = donations.toArray(new Donation[0]);
    }

    @Test
    public void testAddRunner()
    {
        when(runnerRepository.get("runnerId")).thenThrow(new NotFoundException(null));
        service.addRunner(runner);
        verify(runnerRepository).insert(runner);
        verify(cacheService).clearRunners();
    }

    @Test
    public void testAddRunnerExists()
    {
        service.addRunner(runner);
        verify(runnerRepository).update(runner);
        verify(runner).setLocation(eq(new Location(-6.25996, 53.34434)));
        verify(donation1).setCollectedBy("runnerId");
        verify(donation2, never()).setCollectedBy("runnerId");
        verify(donationRepository).update(donation1);
        verify(donationRepository, never()).update(donation2);

        verify(runner).setTotal(4);
        verify(donorRepository).update(donor);
    }

    @Test
    public void testUpdateRunner()
    {
        service.updateRunner(updateRunner);

        verify(runnerRepository).update(runner);
        verify(runner).setLocation(eq(new Location(-6.25996, 53.34434)));
        verify(donation1).setCollectedBy("runnerId");
        verify(donation2, never()).setCollectedBy("runnerId");
        verify(donationRepository).update(donation1);
        verify(donationRepository, never()).update(donation2);

        verify(runner).setTotal(4);
        verify(donor).setTotalCollected(1);
        verify(donor).setTotalUncollected(0);
        verify(donorRepository).update(donor);
        verify(cacheService).clear();
    }

    @Test
    public void testUpdateRunnerNothingCollected()
    {
        when(updateRunner.getLocation()).thenReturn(new Location(123, 123));
        when(runner.getLocation()).thenReturn(new Location(123, 123));
        
        service.updateRunner(updateRunner);

        verify(runnerRepository).update(runner);
        verify(runner).setLocation(eq(new Location(123, 123)));
        verify(donation1, never()).setCollectedBy(anyString());
        verify(donation2, never()).setCollectedBy(anyString());
        verify(donationRepository, never()).update((Donation) anyObject());
        verifyZeroInteractions(donorRepository);
        verify(cacheService).clearRunners();
    }

    @Test
    public void testGetMap()
    {
        Map map = service.getMap(null);
        assertArrayEquals(runnerArray, map.getRunners());
        assertArrayEquals(donationArray, map.getDonations());
        verify(cacheService).setDonations(donations);
    }

    @Test
    public void testGetMapCached()
    {
        when(cacheService.getDonations()).thenReturn(donations);
        when(cacheService.getRunners()).thenReturn(runners);

        Map map = service.getMap(null);

        assertArrayEquals(runnerArray, map.getRunners());
        assertArrayEquals(donationArray, map.getDonations());
        verifyZeroInteractions(donationRepository);
        verifyZeroInteractions(runnerRepository);
    }

    @Test
    public void testGetMapDate()
    {
        Map map = service.getMap(new Date(123));
        assertArrayEquals(runnerArray, map.getRunners());
        assertArrayEquals(donationArray, map.getDonations());
        verify(cacheService).setDonations(donations, new Date(123));
        verify(cacheService).setRunners(runners);
    }

    @Test
    public void testGetMapDateCached()
    {
        when(cacheService.getDonations(new Date(123))).thenReturn(donations);
        when(cacheService.getRunners()).thenReturn(runners);

        Map map = service.getMap(new Date(123));

        assertArrayEquals(runnerArray, map.getRunners());
        assertArrayEquals(donationArray, map.getDonations());
        verifyZeroInteractions(donationRepository);
        verifyZeroInteractions(runnerRepository);
    }
}
