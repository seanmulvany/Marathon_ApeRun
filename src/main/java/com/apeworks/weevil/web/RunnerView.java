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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.View;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.transcoder.Transcoder;

public class RunnerView implements View, ApplicationContextAware
{
    private static final String APPLICATION_JSON = "application/json";

    private static final String RUNNER = "runner";

    private Transcoder<Runner> runnerTranscoder;

    public void setApplicationContext(ApplicationContext context) throws BeansException
    {
        runnerTranscoder = (Transcoder<Runner>) context.getBean("runnerTranscoder");
    }

    public void render(Map<String, ?> map, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        response.setContentType(APPLICATION_JSON);
        runnerTranscoder.encode((Runner) map.get(RUNNER), response.getOutputStream());
    }

    public String getContentType()
    {
        return APPLICATION_JSON;
    }
}
