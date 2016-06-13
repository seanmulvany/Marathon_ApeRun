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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apeworks.weevil.service.MailService;

@Controller
public class MailController
{
    @Autowired
    private MailService mailService;

    public void setMailService(MailService mailService)
    {
        this.mailService = mailService;
    }
    
    @RequestMapping(value = "mail/start", method = RequestMethod.GET)
    public ResponseEntity<Void> sendStartMail()
    {
        mailService.sendStartEventMail();
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "mail/end", method = RequestMethod.GET)
    public ResponseEntity<Void> sendEndMail()
    {
        mailService.sendEndMail();
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
