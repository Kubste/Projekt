package simulation.unit;

import simulation.attack.IAttack;
import simulation.map.ISimulationMap;
import simulation.position.IPosition;
import simulation.position.Position;

import java.util.*;

public abstract class AUnit implements IUnit {

    protected double hp;
    protected int range;
    protected boolean isA;
    protected ISimulationMap map;

    /**
     * Konstruktor obiektu jednostki
     * @param hp ilość punktów wytrzymałości jednostki
     * @param range zasięg jednostki
     * @param isA true -> przynależność jednostki do drużyny A; false -> przynależność jednostki do drużyny B
     * @param map obiekt klasy SimulationMap
     */
    public AUnit(double hp, int range, boolean isA, ISimulationMap map) {
        this.hp = hp;
        this.range = range;
        this.isA = isA;
        this.map = map;
    }

    /**
     * Metoda zwracająca przynależność jednostki do danej drużyny
     * @return true -> przynależność jednostki do drużyny A; false -> przynależność jednostki do drużyny B
     */
    @Override
    public boolean getIsA() {
        return this.isA;
    }

    /**
     * Metoda zwracająca obiekt klasy SimulationMap
     * @return map - obiekt klasy SimulationMap
     */
    @Override
    public ISimulationMap getSimulationMap() {
        return this.map;
    }

    /**
     * Metoda ustawiająca obiekt klasy SimulationMap
     * @param map obiekt klasy SimulationMap
     */
    @Override
    public void setSimulationMap(ISimulationMap map) {
        this.map = map;
    }

    /**
     * Metoda zwracająca informację, czy jednostka "jest żywa"
     * @return true -> jednostka " jest żywa"; false -> jednostka "jest martwa"
     */
    @Override
    public boolean isAlive() {
        if (this.hp > 0) return true;
        else return false;
    }

    /**
     * Metoda zwracająca informację, czy zadana jednostka jest w tej samej drużynie co jednostka wykonująca metodę
     * @param unit obiekt zadanej jednostki
     * @return true -> jednostki są w tej samej drużynie; false -> jednostki są w przeciwnych drużynach
     */
    @Override
    public boolean isFriendly(IUnit unit) {
        if(unit == null) return true;
        if (this.isA == unit.getIsA()) return true;
        else return false;
    }

    /**
     * Metoda przeprowadzająca atak na wybraną jednostkę
     * @param unit obiekt jednostki, która jest celem ataku
     */
    @Override
    public void attack(IUnit unit) {
        unit.takeDamage(this.dealDamage());
        this.takeDamage(unit.dealDamage());
    }

    /**
     * Metoda zwracająca listę pseudolosowo ustawionych obiektów klasy Position, przechowujących współrzędne pól mieszczących się w zasięgu jednostki
     * @return surroundings - lista obiektów przechowujących współrzędne pól w zasięgu jednostki
     */
    @Override
    public List<IPosition> getSurroundings() {
        IPosition unitPosition = this.map.getUnitPosition(this);
        List<IPosition> surroundings = new ArrayList<>();

        int X = unitPosition.getX();
        int Y = unitPosition.getY();
        int xStart = X - range;
        int yStart = Y - range;

            for(int y = yStart; y <= yStart + 2*range; y++) {
                for(int x = xStart; x <= xStart + 2*range; x++) {
                    if(x >= 0 && y >= 0 && x < this.map.getMapSizeX() && y < this.map.getMapSizeY() && !(X == x && Y ==y)) surroundings.add(new Position(x, y));
                }
            }

        Collections.shuffle(surroundings);

        return surroundings;
    }

    /**
     * Metoda przeszukująca pola w zasięgu jednostki, w poszukiwaniu jednostek z przeciwnej drużyny
     * @return obiekt jednostki z przeciwnej drużyny; NULL -> brak jednostek z przeciwnej drużyny w zasięgu jednostki
     */
    @Override
    public IUnit searchForUnit() {
        List<IPosition> surroundings = this.getSurroundings();

        for(IPosition position : surroundings) {
            if(!this.isFriendly(this.map.getUnit(position)) && this.map.getUnit(position).isAlive()) return this.map.getUnit(position);
        }
        return null;
    }

    /**
     * Metoda przemieszczająca obiekt jednostki na pole w jej zasięgu
     */
    @Override
    public void move() {
       List<IPosition> surroundings = this.getSurroundings();

       for(IPosition position : surroundings) {
           if(this.map.getUnit(position) == null) {
               this.map.putUnit(this, position);
               break;
           }
       }
    }

    /**
     * Metoda przeprowadzająca turę jednostki:
     * 1. Sprawdzenie czy jednostka "jest żywa"
     * 2. Przeszukanie pól w zasięgu jednostki w poszukiwaniu wrogich jednostek
     * 3. Zaatakowanie wrogiej jednostki / wykonanie ruchu
     */
    @Override
    public void doAction() {
        if(!this.isAlive()) return;
        IUnit unit = this.searchForUnit();
        if(unit != null) this.attack(unit);
        else this.move();
    }

    /**
     * Metoda przyjmująca wartość zadanych jednostce obrażeń, od obiektu pośredniczącego
     * @param attack obiekt pośredniczący w wymianie informacji o zadanych obrażeniach
     */
    @Override
    public abstract void takeDamage(IAttack attack);

    /**
     * Metoda wysyłająca informację o zadanych przez jednostkę obrażeniach
     * @return obiekt pośredniczący w wymianie informacji o zadawanych obrażeniach
     */
    @Override
    public abstract IAttack dealDamage();
}
