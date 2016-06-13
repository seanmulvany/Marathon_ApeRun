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
package com.apeworks.weevil.web;

import java.util.List;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.domain.Map;

public class Backup
{
    private Map map;

    private List<Donor> donors;

    public Backup(Map map, List<Donor> donors)
    {
        this.map = map;
        this.donors = donors;
    }

    public Map getMap()
    {
        return map;
    }

    public void setMap(Map map)
    {
        this.map = map;
    }

    public List<Donor> getDonors()
    {
        return donors;
    }

    public void setDonors(List<Donor> donors)
    {
        this.donors = donors;
    }

}
