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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GameService
{
    private Date startTime;

    private Date endTime;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Value("${start.time}")
    public void setStartTime(String startTimeProperty) throws Exception
    {
        this.startTime = dateFormat.parse(startTimeProperty);
    }

    @Value("${end.time}")
    public void setEndTime(String endTimeProperty) throws Exception
    {
        this.endTime = dateFormat.parse(endTimeProperty);
    }

    public boolean beforeStart()
    {
        return new Date().before(startTime);
    }

    public boolean afterEnd()
    {
        return new Date().after(endTime);
    }
}
