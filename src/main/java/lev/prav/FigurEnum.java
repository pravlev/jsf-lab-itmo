package lev.prav;

import lev.prav.model.Points;

import java.math.BigDecimal;
import java.util.function.Predicate;

public enum FigurEnum {
    TRIANGLE,
    SQUARE,
    CIRCLE,
    EMPTY;

    public Predicate<Points> check() {
        System.out.println(this);
        switch (this) {
            case CIRCLE: return (model) -> {
                double katet_sum_x2 = Math.pow(Math.abs(model.getX())*2, 2) + Math.pow(Math.abs(model.getY())*2, 2);
                double gipatinusa = Math.pow(katet_sum_x2, 0.5);
                System.out.println(" " + gipatinusa + " " + Math.abs(model.getX()) + " " + Math.abs(model.getY()));
                return gipatinusa <= (double) model.getR();
            };
            case EMPTY: return (model) -> {return false;};
            case SQUARE: return (model) -> {return (Math.abs(model.getX())*2 <= model.getR()) && (Math.abs(model.getY()) <= model.getR());};
            case TRIANGLE: return (model) -> {
                BigDecimal y_koef = new BigDecimal(1.0); // Соотношение катета y  к катету по x в заданном триугольнике
                BigDecimal x_katet = (new BigDecimal(model.getR()).multiply(new BigDecimal(1))).subtract(new BigDecimal(Math.abs(model.getX()))); // При условии, что в заданном триугольнике катет по x равен r
                if (x_katet.compareTo(BigDecimal.ZERO) < 0) {
                    return false;
                }
                BigDecimal y_katet = y_koef.multiply(x_katet);
                System.out.println(" " + x_katet + ' ' + y_katet + ' ' + model.getY());
                return new BigDecimal(Math.abs(model.getY())).compareTo(y_katet) <= 0;
            };
            default: return null;
        }
    }
}
