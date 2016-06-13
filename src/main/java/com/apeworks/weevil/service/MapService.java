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

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.domain.Location;
import com.apeworks.weevil.domain.Map;
import com.apeworks.weevil.domain.Runner;
import com.googlecode.objectify.NotFoundException;

@Service
public class MapService
{
    private static final double METRES = 60 * 1.1515 * 1.609344 * 1000;

    private static final double CONTACT_RADIUS = 40;

    private GameService gameService;

    @Autowired
    public void setGameService(GameService gameService)
    {
        this.gameService = gameService;
    }

    @Autowired
    private RunnerRepository runnerRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private CacheService cacheService;

    public void addRunner(Runner runner)
    {
        try
        {
            Runner existingRunner = runnerRepository.get(runner.getId());
            updateExistingRunner(runner, existingRunner);
        }
        catch (NotFoundException e)
        {
            runnerRepository.insert(runner);
            cacheService.clearRunners();
        }
    }

    public void setDonationRepository(DonationRepository donationRepository)
    {
        this.donationRepository = donationRepository;
    }

    public void setDonorRepository(DonorRepository donorRepository)
    {
        this.donorRepository = donorRepository;
    }

    public void setCacheService(CacheService cacheService)
    {
        this.cacheService = cacheService;
    }

    public void updateRunner(Runner updatedRunner)
    {
        updateExistingRunner(updatedRunner, runnerRepository.get(updatedRunner.getId()));
    }

    private void updateExistingRunner(Runner updatedRunner, Runner existingRunner)
    {
        existingRunner.setLocation(updatedRunner.getLocation());
        existingRunner.setSpeed(updatedRunner.getSpeed());
        existingRunner.setTime(updatedRunner.getTime());
        existingRunner.setDuration(updatedRunner.getDuration());
        existingRunner.setDistance(updatedRunner.getDistance());
        checkDonationsAndUpdateCache(existingRunner);
        runnerRepository.update(existingRunner);
    }

    private void checkDonationsAndUpdateCache(Runner existingRunner)
    {
        if (checkForCollectedDonations(existingRunner))
            cacheService.clear();
        else
            cacheService.clearRunners();
    }

    private boolean checkForCollectedDonations(Runner runner)
    {
        boolean donationsUpdated = false;

        Collection<Donation> donations = getUncollectedDonations();
        for (Donation donation : donations)
        {
            if (!gameService.beforeStart() && isContacting(runner.getLocation(), donation.getLocation()))
            {
                collect(runner, donation);
                donationsUpdated = true;
            }
        }

        return donationsUpdated;
    }

    private void collect(Runner runner, Donation donation)
    {
        donation.setCollectedBy(runner.getId());
        donationRepository.update(donation);
        updateRunnerTotal(runner, donation);
        updateDonor(donation);
    }

    private void updateDonor(Donation donation)
    {
        Donor donor = donorRepository.get(donation.getDonorId());
        donor.setTotalCollected(donor.getTotalCollected() + donation.getAmount());
        donor.setTotalUncollected(donor.getTotalUncollected() - donation.getAmount());
        donorRepository.update(donor);
    }

    private void updateRunnerTotal(Runner runner, Donation donation)
    {
        Runner existingRunner = runnerRepository.get(runner.getId());
        runner.setTotal(existingRunner.getTotal() + donation.getAmount());
    }

    public Map getMap(Date date)
    {
        Map map = new Map();
        map.setRunners(getRunners().toArray(new Runner[0]));
        map.setDonations(getDonations(date).toArray(new Donation[0]));
        return map;
    }

    private Collection<Runner> getRunners()
    {
        Collection<Runner> runners = cacheService.getRunners();
        
        if (runners == null)
        {
            runners = runnerRepository.getRunners();
            cacheService.setRunners(runners);
        }
        
        return runners;
    }

    private Collection<Donation> getDonations(Date date)
    {
        if (date == null)
            return getUncollectedDonations();
        else
            return getUpdatedDonations(date);
    }

    private Collection<Donation> getUpdatedDonations(Date date)
    {
        Collection<Donation> cachedDonations = (Collection<Donation>) cacheService.getDonations(date);
        if (cachedDonations != null)
            return cachedDonations;

        Collection<Donation> repositoryDonations = donationRepository.getDonations(date);
         cacheService.setDonations(repositoryDonations, date);

        return repositoryDonations;
    }

    private Collection<Donation> getUncollectedDonations()
    {
        Collection<Donation> cachedDonations = (Collection<Donation>) cacheService.getDonations();
        if (cachedDonations != null)
            return cachedDonations;

        Collection<Donation> repositoryDonations = donationRepository.getUncollectedDonations();
        cacheService.setDonations(repositoryDonations);

        return repositoryDonations;
    }

    public void setRunnerRepository(RunnerRepository runnerRepository)
    {
        this.runnerRepository = runnerRepository;
    }

    private boolean isContacting(Location location1, Location location2)
    {
        return distance(location1, location2) < CONTACT_RADIUS;
    }

    private double distance(Location location1, Location location2)
    {
        return distance(location1.getY(), location1.getX(), location2.getY(), location2.getX());
    }

    private double distance(double lat1, double lon1, double lat2, double lon2)
    {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * METRES;
        return dist;
    }

    private double deg2rad(double deg)
    {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad)
    {
        return (rad * 180.0 / Math.PI);
    }

}
