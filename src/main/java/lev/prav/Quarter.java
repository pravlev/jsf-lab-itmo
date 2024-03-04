package lev.prav;

public enum Quarter {
    NORTH_WEST(FigurEnum.SQUARE),
    NORTH_EAST(FigurEnum.EMPTY),
    SOUTH_WEST(FigurEnum.CIRCLE),
    SOUTH_EAST(FigurEnum.TRIANGLE);

    public FigurEnum getFigure() {
        return figure;
    }

    private final FigurEnum figure;

    Quarter(FigurEnum figurEnum) {
        this.figure = figurEnum;
    }
}
