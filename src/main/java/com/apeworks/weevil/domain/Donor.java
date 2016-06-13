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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Donor
{
    @Id
    private Long id;

    @Size(min = 1, message = "Please enter a name")
    private String name;

    @Pattern(regexp = "[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,4}", message = "Please enter a valid email address")
    private String email;

    private String pin;

    private int totalCollected;

    private int totalUncollected;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
    }

    public String getPin()
    {
        return pin;
    }

    public void setTotalCollected(int totalCollected)
    {
        this.totalCollected = totalCollected;
    }

    public int getTotalCollected()
    {
        return totalCollected;
    }

    public int getTotalUncollected()
    {
        return totalUncollected;
    }

    public void setTotalUncollected(int totalUncollected)
    {
        this.totalUncollected = totalUncollected;
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
        Donor other = (Donor) obj;
        if (email == null)
        {
            if (other.email != null)
                return false;
        }
        else if (!email.equals(other.email))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (pin == null)
        {
            if (other.pin != null)
                return false;
        }
        else if (!pin.equals(other.pin))
            return false;
        return true;
    }
}
