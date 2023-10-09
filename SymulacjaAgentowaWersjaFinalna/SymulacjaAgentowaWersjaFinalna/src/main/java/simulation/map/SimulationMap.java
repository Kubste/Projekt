package simulation.map;

import simulation.position.IPosition;
import simulation.position.Position;
import simulation.unit.IUnit;

import java.util.HashMap;
import java.util.Map;

public class SimulationMap implements ISimulationMap {

    private IUnit[][] map;
    private Map<IUnit, IPosition> unitsPositions;

    /**
     * Konstruktor obiektu mapy symulacji
     * @param mapSizeX rozmiar mapy w osi X
     * @param mapSizeY rozmiar mapy w osi Y
     */
    public SimulationMap(int mapSizeX, int mapSizeY) {
        map = new IUnit[mapSizeX][mapSizeY];
        unitsPositions = new HashMap<>();
    }

    /**
     * Metoda zwracająca pożądany obiekt jednostki
     * @param position obiekt pozycji, zawierający współrzędne poszukiwanego obiektu jednostki
     * @return IUnit - obiekt jednostki
     */
    @Override
    public IUnit getUnit(IPosition position) {
        return map[position.getX()][position.getY()];
    }

    /**
     * Metoda zwracająca rozmiar mapy symulacji w osi X
     * @return mapSizeX - rozmiar mapy w osi X
     */
    @Override
    public int getMapSizeX() {
        return map.length;
    }

    /**
     * Metoda zwracająca rozmiar mapy symulacji w osi Y
     * @return mapSizeY - rozmiar mapy w osi Y
     */
    @Override
    public int getMapSizeY() {
        return map[0].length;
    }

    /**
     * Metoda zwracająca tablicę dwuwymiarową, zawierającą obiekty jednostek oraz pola NULL
     * @return map - mapa symulacji
     */
    @Override
    public IUnit[][] getMap() {
        return map;
    }

    /**
     * Metoda ustawiająca obiekt jednostki na zadanym polu mapy
     * @param unit obiekt jednostki, która jest przenoszona
     * @param position pozycja na którą przenoszona jest jednostka
     * @return true -> przeniesienie powiodło się, false -> wybrana pozycja jest już zajęta
     */
    @Override
    public boolean putUnit(IUnit unit, IPosition position) {
        IPosition actualPosition = getUnitPosition(unit);

        if(map[position.getX()][position.getY()] != null) return false;

        if(actualPosition.getX() >= 0 && actualPosition.getY() >= 0) map[actualPosition.getX()][actualPosition.getY()] = null;

        map[position.getX()][position.getY()] = unit;
        unitsPositions.put(unit, position);
        //unit.setSimulationMap(this);

        return true;
    }

    /**
     * Metoda zwracająca obiekt pozycji jednostki
     * @param unit obiekt jednostki, której pozycja jest zwracana
     * @return position - obiekt pozycji przechowujący współrzędne obiektu jednostki; x == -1 && y == -1 -> dla jednostki nie posiadającej jeszcze ustalonej pozycji
     */
    @Override
    public IPosition getUnitPosition(IUnit unit) {
        IPosition position = unitsPositions.get(unit);
        if(position == null) return new Position(-1,-1);
        else return position;
    }

    /**
     * Metoda usuwająca obiekt jednostki, jeśli jest on "martwy"
     * @param unit obiekt jednostki, na którym wykonywana jest metoda
     * @return true -> obiekt został usunięty, false -> obiekt nie został usunięty
     */
    @Override
    public boolean destroyUnit(IUnit unit) {
        if(!unit.isAlive()) {
            map[unit.getSimulationMap().getUnitPosition(unit).getX()][unit.getSimulationMap().getUnitPosition(unit).getY()] = null;
            unitsPositions.remove(unit);
            //unit.setSimulationMap(this);
            return true;
        }
        return false;
    }
}
