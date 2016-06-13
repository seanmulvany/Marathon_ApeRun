package com.apeworks.weevil.web;

/**
 * Created by seanmulvany on 11/06/2016.
 */

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import com.apeworks.weevil.service.RunnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apeworks.weevil.domain.Run;
import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.exception.EncodingException;
import com.apeworks.weevil.exception.NotLoggedInException;
import com.apeworks.weevil.service.RunService;
import com.apeworks.weevil.service.GameService;
import com.apeworks.weevil.transcoder.Transcoder;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RunController {
    private static final String IF_RANGE = "ifRange";

    @Autowired
    private RunService runService;

    @Autowired
    private Transcoder<Run> runTranscoder;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private GameService gameService;

    @Autowired
    private RunnerService runnerService;

    @Autowired
    private Transcoder<Runner> runnerTranscoder;

    public void setRunService(RunService runService)
    {
        this.runService = runService;
    }

    public void setRunTranscoder(Transcoder<Run> runTranscoder)
    {
        this.runTranscoder = runTranscoder;
    }

    public void setAuthenticationService(AuthenticationService authenticationService)
    {
        this.authenticationService = authenticationService;
    }

    public void setGameService(GameService gameService)
    {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/run", method = RequestMethod.POST)
    public ResponseEntity<Void> createRun(HttpServletRequest request) throws EncodingException, IOException, NotLoggedInException,  URISyntaxException
    {
        if (gameService.afterEnd())
            return new ResponseEntity<Void>(HttpStatus.GONE);


        Run run = runTranscoder.decode(request.getInputStream());
        runService.create(run, getLoggedInRunner(request));

        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(new URI(request.getRequestURL().append("/").append(run.getId()).toString()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    private Runner getLoggedInRunner(HttpServletRequest request) throws NotLoggedInException
    {
        Runner runner = authenticationService.getLoggedInRunner(request);
        if (runner == null)
            throw new NotLoggedInException();
        return runner;
    }


}
