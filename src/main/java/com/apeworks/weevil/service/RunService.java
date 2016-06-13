package com.apeworks.weevil.service;

/**
 * Created by seanmulvany on 11/06/2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apeworks.weevil.domain.Runner;
import com.apeworks.weevil.domain.Run;

@Service
public class RunService {

    @Autowired
    private CacheService cache;

    @Autowired
    private RunnerRepository runnerRepository;

    @Autowired
    private RunRepository runRepository;

    public void setRunRepository(RunRepository runRepository)
    {
        this.runRepository = runRepository;
    }

    public void setRunnerRepository(RunnerRepository runnerRepository)
    {
        this.runnerRepository = runnerRepository;
    }

    public void setCache(CacheService cache)
    {
        this.cache = cache;
    }

    public void create(Run run, Runner runner)
    {
        run.setRunnerId(runner.getId());
        run.setTime(System.currentTimeMillis());
        runRepository.insert(run);
        cache.clear();
    }



}
