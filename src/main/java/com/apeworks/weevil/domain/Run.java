package com.apeworks.weevil.domain;

/**
 * Created by seanmulvany on 11/06/2016.
 */


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Run implements Serializable{

    @Id
    private Long id;

    private long time; //timestamp on the run

    private String route;

    private int duration; //run duration in seconds

    private String runnerId;


    public Run (){

    }

    public Run(String route, int duration){
        this.route = route;
        this.duration = duration;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(String runnerId) {
        this.runnerId = runnerId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Run other = (Run) obj;
        if (route != other.route)
            return false;
        if (duration != other.duration)
            return false;
        if(time != other.time)
            return false;
        if(runnerId != other.runnerId)
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
