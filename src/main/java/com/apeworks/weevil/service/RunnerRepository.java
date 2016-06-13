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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.apeworks.weevil.domain.Runner;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

@Repository
public class RunnerRepository
{
    @Autowired
    private ObjectifyFactory objectifyFactory;

    public void setObjectifyFactory(ObjectifyFactory objectifyFactory)
    {
        this.objectifyFactory = objectifyFactory;
    }

    public void insert(Runner runner)
    {
        Objectify objectify = objectifyFactory.begin();
        objectify.put(runner);
    }

    public void update(Runner runner)
    {
        Objectify objectify = objectifyFactory.begin();
        objectify.put(runner);
    }

    public Collection<Runner> getRunners()
    {
        Objectify objectify = objectifyFactory.begin();
        return objectify.query(Runner.class).list();
    }

    public Runner get(String id)
    {
        try
        {
            Objectify objectify = objectifyFactory.begin();
            return objectify.get(Runner.class, id);
        }
        catch (NotFoundException e)
        {
            return null;
        }
    }
}
