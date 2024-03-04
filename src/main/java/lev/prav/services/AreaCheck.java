package lev.prav.services;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import lev.prav.Quarter;
import lev.prav.Utility;
import lev.prav.model.Points;

import java.io.Serializable;
import java.util.Date;

@ManagedBean
@SessionScoped
@Named(value = "AreaCheck")
public class AreaCheck implements Serializable, AreaCheckInt{
    @Override
    public void CheckHit(Points points) {
        long startTime = System.nanoTime();
        points.setDate(new Date(System.currentTimeMillis()));
        points.setResult(check(points));
        points.setDuration((int) (System.nanoTime() - startTime));
    }

    private boolean check(Points points) {
        Quarter quarter = Utility.find(points.getX(), points.getY());
        return quarter.getFigure().check().test(points);
    }
}
