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

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.geronimo.mail.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.apeworks.weevil.domain.Donor;
import com.apeworks.weevil.domain.Runner;

@Service
public class AuthenticationService
{
    private static final int COOKIE_AGE = 7 * 24 * 60 * 60;

    private Pattern donorCookiePattern = Pattern.compile("([0-9]+) (.*)");

    private Pattern runnerCookiePattern = Pattern.compile("(.+)\\|(.+)");

    @Value("${signature.key}")
    private String signatureKey;

    public void setSignatureKey(String signatureKey)
    {
        this.signatureKey = signatureKey;
    }

    public Donor getLoggedInDonor(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();

        if (cookies == null)
            return null;

        for (int i = 0; i < cookies.length; i++)
        {
            if (isLoginCookie(cookies[i], "DONOR"))
                return parseDonor(cookies[i]);
        }
        return null;
    }

    private Donor parseDonor(Cookie cookie)
    {
        Matcher matcher = donorCookiePattern.matcher(cookie.getValue());

        Donor donor = new Donor();
        if (matcher.matches())
        {
            donor.setId(Long.parseLong(matcher.group(1)));
            donor.setName(matcher.group(2));
        }
        return donor;
    }

    private boolean isLoginCookie(Cookie cookie, String name)
    {
        return cookie.getName().equals(name);
    }

    public Cookie createLoginCookie(Donor donor)
    {
        Cookie cookie = new Cookie("DONOR", donor.getId() + " " + donor.getName());
        cookie.setMaxAge(COOKIE_AGE);
        cookie.setPath("/");
        return cookie;
    }

    public Cookie createLoginCookie(Runner runner)
    {
        Cookie cookie = new Cookie("RUNNER", runner.getId() + "|" + getSignature(runner.getId()));
        cookie.setPath("/");
        cookie.setMaxAge(COOKIE_AGE);
        return cookie;
    }

    private String getSignature(String id)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(new String(id + signatureKey).getBytes("utf8"));
            return new String(Base64.encode(digest.digest()), "US-ASCII");
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public Runner getLoggedInRunner(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();

        if (cookies == null)
            return null;

        for (int i = 0; i < cookies.length; i++)
        {
            if (isLoginCookie(cookies[i], "RUNNER"))
                return parseRunner(cookies[i]);
        }
        return null;
    }

    private Runner parseRunner(Cookie cookie)
    {
        Matcher matcher = runnerCookiePattern.matcher(cookie.getValue());
        if (!matcher.matches())
            return null;
        String id = matcher.group(1);
        String signature = matcher.group(2);
        if (id != null && signature != null && signature.equals(getSignature(id)))
            return new Runner(id);
        else
            return null;
    }
}
