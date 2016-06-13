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

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;
import org.springframework.stereotype.Component;

@Component
public class DateUtilTest
{
    private DateUtil dateUtil = new DateUtil();
    
    @Test
    public void testCommutative() throws Exception
    {
        Date date = new Date();
        assertEquals(date.toString(), dateUtil.parseDate(dateUtil.formatDate(date)).toString());
    }
    
    @Test
    public void testNullParse() throws Exception
    {
        assertEquals(null, dateUtil.parseDate(null));
    }
    
    @Test
    public void testNullFormat() throws Exception
    {
        assertEquals(null, dateUtil.formatDate(null));
    }
    
    @Test
    public void testParseBad() throws Exception
    {
        assertEquals(null, dateUtil.parseDate("blah"));
    }
}
