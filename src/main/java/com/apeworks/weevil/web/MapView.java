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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.View;

import com.apeworks.weevil.domain.Donation;
import com.apeworks.weevil.domain.Map;
import com.apeworks.weevil.transcoder.AbstractJsonTranscoder;

public class MapView implements View, ApplicationContextAware
{
    private static final String IF_RANGE = "ifRange";

    private static final String APPLICATION_JSON = "application/json";

    private static final String MAP = "map";

    private AbstractJsonTranscoder<Map> mapTranscoder;

    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        mapTranscoder = (AbstractJsonTranscoder<Map>) context.getBean("mapTranscoder");
    }

    public void render(java.util.Map<String, ?> modelMap, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Map map = (Map) modelMap.get(MAP);
        response.setContentType(APPLICATION_JSON);
        map.setLastModified(getLastUpdated(map, request));
        mapTranscoder.encode(map, response.getOutputStream());
    }

    private Long getLastUpdated(Map map, HttpServletRequest request)
    {
        Donation[] donations = map.getDonations();
        if (donations != null && donations.length > 0)
            return donations[donations.length - 1].getUpdated().getTime();
        else
            return getRequestedTime(request);
    }

    private Long getRequestedTime(HttpServletRequest request)
    {
        String parameter = request.getParameter(IF_RANGE);
        return parameter != null ? Long.parseLong(parameter) : null;
    }

    public String getContentType()
    {
        return APPLICATION_JSON;
    }
}
