package lev.prav.model;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.*;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "points")
@SessionScoped
@Named(value = "points")
@ToString
public class Points implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    double x;
    double y;
    double r;
    boolean result;
    int duration;
    Date date;

    public Points(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;

    }

    public Points() {
    }

    public Coordinate getCoordinates() {
        return new Coordinate(x, y, r, result);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
}
