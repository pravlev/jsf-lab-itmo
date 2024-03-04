package lev.prav.services;

import jakarta.annotation.ManagedBean;
import jakarta.el.MethodExpression;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.LocalDateTime;

@ManagedBean
@Named("clock")
@ApplicationScoped
public class Clock {
    private LocalDateTime dateTime = LocalDateTime.now();

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void update() {
        dateTime = LocalDateTime.now();
    }
}
