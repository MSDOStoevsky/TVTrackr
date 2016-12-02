package com.emptystudio.tvtrackr;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dylan on 9/24/2016.
 */
public class Show implements Serializable {

    private String name;
    private String description;
    private List<String> genres;
    private List<String> schedule;
    private String airTime;
    private String status;
    private String imageURL;

    public Show() {}

    public Show(String name, String description, List<String> genres, List<String> schedule, String airTime, String status, String imageURL) {
        this.name = name;
        this.description = description;
        this.genres = genres;
        this.schedule = schedule;
        this.airTime = airTime;
        this.status = status;
        this.imageURL = imageURL;
    }

    //--- getters
    public String getName(){
        return name;
    }

    public String getDescription() { return description; }

    public String getGenres(){
        return join(genres, ", ");
    }

    public String getSchedule(){
        return join(schedule, ", ");
    }

    public String getAirTime() { return airTime; }

    public String getStatus() {
        return status;
    }

    public String getImageURL() { return imageURL; }

    //--- setters
    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description) { this.description = description; }

    public void setGenres(List<String> genres){
        this.genres = genres;
    }

    public void setSchedule(List<String> schedule){
        this.schedule = schedule;
    }

    public void setAirTime(String airTime) { this.airTime = airTime; }

    public void setStatus(String status) { this.status = status; }

    public void setImageURL(String imageURL) { this.imageURL = imageURL; }

    @Override
    public String toString(){
        return "name=" + this.name + "|description=" + this.description+"|genres=" + this.genres
                +"|schedule="+this.schedule+"|time=" + this.airTime + "|status=" + this.status + "|image="+this.imageURL;
    }

    /*
        Collection to String over given delimiter
     */
    private static String join(Collection<?> s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        Iterator<?> iter = s.iterator();
        while (iter.hasNext()) {
            builder.append(iter.next());
            if (!iter.hasNext()) {
                break;
            }
            builder.append(delimiter);
        }

        return builder.toString();
    }

    private boolean equals(Show show) {
        return this.name.equals(show.getName());
    }
}
