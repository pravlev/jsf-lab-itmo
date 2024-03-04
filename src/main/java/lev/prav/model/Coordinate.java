package lev.prav.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class Coordinate {
    private final double x;
    private final double y;
    private final double r;
    private final boolean result;
}
