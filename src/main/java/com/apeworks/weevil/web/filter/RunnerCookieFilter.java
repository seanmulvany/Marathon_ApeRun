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
package com.apeworks.weevil.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.web.AuthenticationService;

public class RunnerCookieFilter extends GenericFilterBean
{
    private AuthenticationService authenticationService;

    @Override
    protected void initFilterBean() throws ServletException
    {
        authenticationService = (AuthenticationService) WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("authenticationService");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        Runner runner = authenticationService.getLoggedInRunner(((HttpServletRequest) request));
        if (runner != null) {
            request.setAttribute("LOGGED_IN_RUNNER", runner);
            chain.doFilter(request, response);
        }
        else
        {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
