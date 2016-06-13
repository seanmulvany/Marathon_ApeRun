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
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.domain.Runner;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

@Repository
public class DonationRepository
{
    @Autowired
    private ObjectifyFactory objectifyFactory;

    public void setObjectifyFactory(ObjectifyFactory objectifyFactory)
    {
        this.objectifyFactory = objectifyFactory;
    }

    public void insert(Donation donation)
    {
        getObjectify().put(donation);
    }

    public Collection<Donation> getUncollectedDonations()
    {
        return getObjectify().query(Donation.class).filter("collectedBy", null).order("updated").list();
    }

    public Collection<Donation> getDonations(Date updatedSince)
    {
        Query<Donation> query = getObjectify().query(Donation.class);

        if (updatedSince != null)
            query = query.filter("updated >=", updatedSince).order("updated");

        return query.order("updated").list();
    }

    public void update(Donation donation)
    {
        getObjectify().put(donation);
    }

    public int getTotalCollected(Runner runner)
    {
        List<Donation> donations = getObjectify().query(Donation.class).filter("collectedBy", runner.getId()).list();

        return getTotal(donations);
    }

    private int getTotal(List<Donation> donations)
    {
        int total = 0;

        for (Donation donation : donations)
            total += donation.getAmount();

        return total;
    }

    public int getTotalCollected(Donor donor)
    {
        List<Donation> donations = getObjectify().query(Donation.class).filter("donorId", donor.getId()).filter("collectedBy !=", null).list();

        return getTotal(donations);
    }

    public int getTotalUncollected(Donor donor)
    {
        List<Donation> donations = getObjectify().query(Donation.class).filter("donorId", donor.getId()).filter("collectedBy", null).list();

        return getTotal(donations);
    }

    private Objectify getObjectify()
    {
        Objectify objectify = objectifyFactory.begin();
        return objectify;
    }

    public void deleteAll()
    {
        Objectify objectify = getObjectify();
        objectify.delete(objectify.query(Donation.class).list());
    }
}
