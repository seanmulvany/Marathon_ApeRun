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

import com.apeworks.weevil.domain.Donor;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;

@Repository
public class DonorRepository
{
    @Autowired
    private ObjectifyFactory objectifyFactory;

    public void setObjectifyFactory(ObjectifyFactory objectifyFactory)
    {
        this.objectifyFactory = objectifyFactory;
    }

    public void insert(Donor donor) throws EmailInUseException
    {
        Objectify objectify = objectifyFactory.begin();
        checkUnique(donor, objectify);
        objectify.put(donor);
    }

    private void checkUnique(Donor donor, Objectify objectify) throws EmailInUseException
    {
        Donor existingByEmail = getByEmail(donor.getEmail(), objectify);
        if (existingByEmail != null)
            throw new EmailInUseException(donor.getEmail());
    }

    public void update(Donor donor)
    {
        Objectify objectify = objectifyFactory.begin();
        objectify.put(donor);
    }

    public Collection<Donor> getDonors()
    {
        Objectify objectify = objectifyFactory.begin();
        return objectify.query(Donor.class).list();
    }

    public Donor getByEmail(String email)
    {
        Objectify objectify = objectifyFactory.begin();
        return getByEmail(email, objectify);
    }

    public Donor get(long id)
    {
        Objectify objectify = objectifyFactory.begin();
        return objectify.get(Donor.class, id);
    }

    private Donor getByEmail(String email, Objectify objectify)
    {
        return objectify.query(Donor.class).filter("email", email).get();
    }

    private Donor getByName(String name, Objectify objectify)
    {
        return objectify.query(Donor.class).filter("name", name).get();
    }
}
