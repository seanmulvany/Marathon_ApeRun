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

public class Map
{
    private Runner[] runners;

    private Donation[] donations;

    private Long lastModified;
    
    public Map()
    {
    }

    public void setRunners(Runner[] runners)
    {
        this.runners = runners;
    }

    public Runner[] getRunners()
    {
        return runners;
    }

    public Donation[] getDonations()
    {
        return donations;
    }

    public void setDonations(Donation[] donations)
    {
        this.donations = donations;
    }

    public void setLastModified(Long lastModified)
    {
        this.lastModified = lastModified;
    }

    public Long getLastModified()
    {
        return lastModified;
    }
}
