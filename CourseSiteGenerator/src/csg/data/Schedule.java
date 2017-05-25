/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author isoni
 */
public class Schedule<E extends Comparable<E>> implements Comparable<E>  {
    private StringProperty type;
    private StringProperty date;
    private StringProperty time;
    private StringProperty title;
    private StringProperty topic;
    private StringProperty link;
    private StringProperty criteria;
    
    public Schedule(String type, LocalDate date, String title, String topic, String link, String time, String criteria) {
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date.toString());
        this.time = new SimpleStringProperty(time);
//        if(title.equals("")) 
//            this.title = title+"<br /><br /><br />";
//        else
//            this.title = "<br /><br /><br /><br />";
        this.title = new SimpleStringProperty(title);
        this.topic = new SimpleStringProperty(topic);
        this.link = new SimpleStringProperty(link);
        this.criteria = new SimpleStringProperty(criteria);
    }
    
    public String getType() {
        return type.get();
    }
    
    public LocalDate getDate() {
        return LocalDate.parse(date.get());
    }
    
    public String getTime() {
        return time.get();
    }
    
    public String getTitle() {
        return title.get();
    }
    
    public String getTopic() {
        return topic.get();
    }
    
    public String getLink() {
        return link.get();
    }
    
    public String getCriteria() {
        return criteria.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public void setDate(LocalDate date) {
        this.date.set(date.toString());
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public void setCriteria(String criteria) {
        this.criteria.set(criteria);
    }
    
    

    @Override
    public int compareTo(E o) {
       return this.getDate().compareTo((ChronoLocalDate)((Schedule)o).getDate());
    }
}
