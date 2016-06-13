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
package com.apeworks.weevil.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apeworks.weevil.domain.Runner;

@Service
public class RunnerService
{
    @Autowired
    private RunnerRepository runnerRepository;

    public void setRunnerRepository(RunnerRepository runnerRepository)
    {
        this.runnerRepository = runnerRepository;
    }

    public Runner login(Runner runnerCredentials)
    {
        Runner runner = runnerRepository.get(runnerCredentials.getId());
        return runner != null && runner.getPin().equals(runnerCredentials.getPin()) ? runner : null;
    }

    public Runner getRunner(String id)
    {
        return runnerRepository.get(id);
    }

    public Collection<Runner> getRunners()
    {
        return runnerRepository.getRunners();
    }

    public void addRunner(Runner runner)
    {
        runnerRepository.insert(runner);
    }
}
