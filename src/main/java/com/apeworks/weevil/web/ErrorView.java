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

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.apeworks.weevil.exception.NotLoggedInException;

public class ErrorView extends AbstractView
{
    private static final String APPLICATION_JSON = "application/json";
    private static final Logger LOG = Logger.getLogger(ErrorView.class.getName());

    private Map<Class<? extends Exception>, Integer> map = new HashMap<Class<? extends Exception>, Integer>();

    public ErrorView()
    {
        map.put(NotLoggedInException.class, HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Exception exception = (Exception) map.get("exception");
        LOG.log(Level.WARNING, exception.getMessage(), exception);
        response.setStatus(getStatus(exception));
        response.setContentType(APPLICATION_JSON);
        new PrintStream(response.getOutputStream()).println(exception.getMessage());
    }

    private int getStatus(Exception exception)
    {
        Integer status = map.get(exception);
        return status != null ? status : HttpServletResponse.SC_BAD_REQUEST;
    }
}
