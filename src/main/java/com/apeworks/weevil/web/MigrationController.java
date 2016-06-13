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

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.service.DonationRepository;
import com.apeworks.weevil.service.DonorRepository;
import com.apeworks.weevil.service.MapService;
import com.apeworks.weevil.transcoder.Transcoder;

//@Controller
public class MigrationController
{
    private MapService mapService;

    private DonorRepository donorRepository;

    @Autowired
    public void setMapService(MapService mapService)
    {
        this.mapService = mapService;
    }

    @Autowired
    public void setRunnerTranscoder(Transcoder<Runner> runnerTranscoder)
    {
    }

    @Autowired
    public void setDonorRepository(DonorRepository donorRepository)
    {
        this.donorRepository = donorRepository;
    }

    @Autowired
    public void setDonationRepository(DonationRepository donationRepository)
    {
    }

    @RequestMapping(value = "/migration/backup", method = RequestMethod.GET)
    public String backup(ModelMap modelMap) throws EncodingException, IOException
    {
        modelMap.put("map", mapService.getMap(null));
        modelMap.put("donors", donorRepository.getDonors());
        return "backup";
    }
}
