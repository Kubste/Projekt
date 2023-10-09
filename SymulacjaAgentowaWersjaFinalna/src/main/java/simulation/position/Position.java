package simulation.position;

public class Position implements IPosition {

    private int x;
    private int y;

    /**
     * Konstruktor obiektu pozycji, przechowującego współrzędne obiektu jednostki
     * @param x współrzędna X
     * @param y współrzędna Y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Metoda zwracająca wartość współrzędnej X
     * @return x - wartość współrzędnej X
     */
    @Override
    public int getX() {
        return this.x;
    }

    /**
     * Metoda zwracająca wartość współrzędnej Y
     * @return y - wartość współrzędnej Y
     */
    @Override
    public int getY() {
        return this.y;
    }

    /**
     * Metoda ustawiająca zadaną wartość współrzędnej X
     * @param x zadana wartość współrzędnej X
     */
    @Override
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Metoda ustawiająca zadaną wartość współrzędnej Y
     * @param y zadana wartość współrzędnej Y
     */
    @Override
    public void setY(int y) {
        this.y = y;
    }
}
