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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.domain.Map;
import com.apeworks.weevil.transcoder.AbstractJsonTranscoder;
import com.apeworks.weevil.transcoder.Transcoder;

public class BackupView implements View
{
    private static final String APPLICATION_JSON = "application/json";

    private static final String MAP = "map";

    private static final String DONORS = "donors";

    public void render(java.util.Map<String, ?> modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Transcoder<Backup> transcoder = createTranscoder();
        Map map = (Map) modelMap.get(MAP);
        List<Donor> donors = (List<Donor>) modelMap.get(DONORS);
        transcoder.encode(new Backup(map, donors), response.getOutputStream());
    }

    private Transcoder<Backup> createTranscoder()
    {
        return new AbstractJsonTranscoder<Backup>(Backup.class);
    }

    public String getContentType()
    {
        return APPLICATION_JSON;
    }
}
