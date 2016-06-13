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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class DateUtil
{
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    private static final Logger LOG = Logger.getLogger(MapController.class.getName());

    private ThreadLocal<DateFormat> dateFormat = new ThreadLocal<DateFormat>()
    {
        @Override
        protected DateFormat initialValue()
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_RFC1123);
            dateFormat.setLenient(false);
            return dateFormat;
        }
    };

    public Date parseDate(String date)
    {
        if (date == null)
            return null;

        try
        {
            return dateFormat.get().parse(date);
        }
        catch (Exception e)
        {
            LOG.warning("Failed to parse if-range date [" + date + "]: " + e.getMessage());
            return null;
        }
    }

    public String formatDate(Date date)
    {
        if (date == null)
            return null;

        return dateFormat.get().format(date);
    }
}
