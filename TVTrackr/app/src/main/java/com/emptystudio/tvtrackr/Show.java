package com.emptystudio.tvtrackr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Dylan on 9/24/2016.
 */
public class Show {

    private String name;
    private ArrayList<String> genres;
    private String schedule;
    private String image;

    public Show(){}

    public Show(String name, ArrayList<String> genres, String schedule, String image){
        super();
        this.name = name;
        this.genres = genres;
        this.schedule = schedule;
        this.image = image;
    }

    //--- getters
    public String getName(){
        return name;
    }

    public String getGenres(){
        return join(genres, "|");
    }

    public String getSchedule(){
        return schedule;
    }

    public String getImage(){
        return image;
    }

    //--- setters
    public void setName(String name){
        this.name = name;
    }

    public void setImage(String image){
        this.image = image;
    }

    public void setGenres(ArrayList<String> genres){
        this.genres = genres;
    }

    public void setSchedule(String schedule){
        this.schedule = schedule;
    }

    @Override
    public String toString(){
        return this.name;
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

}
