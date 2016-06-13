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
package com.apeworks.weevil.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class Donation implements Serializable
{
    @Id
    private Long id;

    private int amount;

    @Embedded
    private Location location;

    private long donorId;

    private String collectedBy;

    private Date updated;

    public Donation()
    {
    }

    public Donation(int amount, Location location)
    {
        this.amount = amount;
        this.location = location;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public void setDonorId(long donorId)
    {
        this.donorId = donorId;
    }

    public long getDonorId()
    {
        return donorId;
    }

    public void setCollectedBy(String collectedBy)
    {
        this.collectedBy = collectedBy;
    }

    public String getCollectedBy()
    {
        return collectedBy;
    }

    public Date getUpdated()
    {
        return updated;
    }

    public void setUpdated(Date updated)
    {
        this.updated = updated;
    }

    @PrePersist
    public void update()
    {
        setUpdated(new Date((System.currentTimeMillis() / 1000) * 1000));
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Donation other = (Donation) obj;
        if (amount != other.amount)
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (location == null)
        {
            if (other.location != null)
                return false;
        }
        else if (!location.equals(other.location))
            return false;
        return true;
    }

}
