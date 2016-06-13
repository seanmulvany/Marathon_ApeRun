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
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.service.GameService;
import com.apeworks.weevil.service.MapService;
import com.apeworks.weevil.service.RunnerService;
import com.apeworks.weevil.transcoder.Transcoder;

@Controller
public class MapController
{
    private static final String IF_RANGE = "ifRange";

    private MapService mapService;

    private RunnerService runnerService;

    private Transcoder<Runner> runnerTranscoder;

    private DateUtil dateUtil;

    private GameService gameService;

    @Autowired
    public void setMapService(MapService mapService)
    {
        this.mapService = mapService;
    }

    @Autowired
    public void setRunnerTranscoder(Transcoder<Runner> runnerTranscoder)
    {
        this.runnerTranscoder = runnerTranscoder;
    }

    @Autowired
    public void setDateUtil(DateUtil dateUtil)
    {
        this.dateUtil = dateUtil;
    }

    @Autowired
    public void setRunnerService(RunnerService runnerService)
    {
        this.runnerService = runnerService;
    }

    @Autowired
    public void setGameService(GameService gameService)
    {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/addRunner", method = RequestMethod.POST)
    public String addRunner(HttpServletRequest request, ModelMap modelMap) throws EncodingException, IOException
    {
        Runner runner = (Runner) request.getAttribute("LOGGED_IN_RUNNER");
        Runner storedRunner = runnerService.getRunner(runner.getId());
        storedRunner.setPin(null);
        modelMap.put("runner", storedRunner);
        return "runner";
    }

    @RequestMapping(value = "/updateRunner", method = RequestMethod.PUT)
    public String update(@RequestParam(required = false, value = IF_RANGE) String ifRange, HttpServletRequest request, ModelMap modelMap) throws EncodingException, IOException
    {
        if (gameService.afterEnd()){
            return getMap(ifRange, modelMap);}


        mapService.updateRunner(runnerTranscoder.decode(request.getInputStream()));
        modelMap.put("map", mapService.getMap(parseDate(ifRange)));
        return "map";


    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String getMap(@RequestParam(required = false, value = IF_RANGE) String ifRange, ModelMap modelMap) throws EncodingException, IOException
    {
        modelMap.put("map", mapService.getMap(parseDate(ifRange)));
        return "map";
    }

    private Date parseDate(String ifRange)
    {
        if (ifRange != null)
        {
            try
            {
                return new Date(Long.parseLong(ifRange));
            }
            catch (NumberFormatException e)
            {}
        }

        return null;
    }
}
