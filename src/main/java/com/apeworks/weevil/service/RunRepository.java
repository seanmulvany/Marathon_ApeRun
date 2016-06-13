package com.apeworks.weevil.service;

import org.springframework.stereotype.Repository;

/**
 * Created by seanmulvany on 11/06/2016.
 */

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.apeworks.weevil.domain.Run;
import com.apeworks.weevil.domain.Runner;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.Query;

@Repository
public class RunRepository {
    @Autowired
    private ObjectifyFactory objectifyFactory;

    public void setObjectifyFactory(ObjectifyFactory objectifyFactory)
    {
        this.objectifyFactory = objectifyFactory;
    }

    private Objectify getObjectify()
    {
        Objectify objectify = objectifyFactory.begin();
        return objectify;
    }

    public void insert(Run run)
    {
        getObjectify().put(run);
    }

    //get a collection of runs by all runners since a certain date
    public Collection<Run> getRuns(Long created)
    {
        Query<Run> query = getObjectify().query(Run.class);

        if (created != null)
            query = query.filter("time", created).order("time");

        return query.order("time").list();
    }

    //get a list of the runs by a runner
    public Collection<Run> getRunnersRuns(Runner runner)
    {
        List<Run> runs = getObjectify().query(Run.class).filter("runnerId", runner.getId()).order("time").list();

        return runs;
    }

}
