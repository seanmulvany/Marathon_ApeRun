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
import java.util.Collections;
import java.util.Date;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import org.springframework.stereotype.Service;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.domain.Run;

@Service
public class CacheService
{
    private static final Logger LOG = Logger.getLogger(CacheService.class.getName());

    private static final String ALL_DONATIONS = "all_donations";

    private static final String ALL_RUNNERS = "all_runners";

    private static final String ALL_RUNS = "all_runs";

    Cache cache;

    public CacheService()
    {
        try
        {
            cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
        }
        catch (CacheException e)
        {
            LOG.severe("Failed to create cache: " + e.getMessage());
        }
    }

    public Collection<Donation> getDonations()
    {
        return (Collection<Donation>) cache.get(ALL_DONATIONS);
    }

    public Collection<Donation> getDonations(Date date)
    {
        return (Collection<Donation>) cache.get(date.getTime());
    }

    public void setDonations(Collection<Donation> donations)
    {
        cache.put(ALL_DONATIONS, donations);
    }

    public void setDonations(Collection<Donation> donations, Date date)
    {
        cache.put(date.getTime(), donations);
    }

    public Collection<Runner> getRunners()
    {
        return (Collection<Runner>) cache.get(ALL_RUNNERS);
    }

    public void setRunners(Collection<Runner> runners)
    {
        cache.put(ALL_RUNNERS, runners);
    }

    public Collection<Run> getRuns() { return (Collection<Run>) cache.get(ALL_RUNS);}

    public void setRuns(Collection<Run> runs)
    {
        cache.put(ALL_RUNS, runs);
    }

    public void clear()
    {
        cache.clear();
    }

    public void clearRunners()
    {
        cache.remove(ALL_RUNNERS);
    }
}
