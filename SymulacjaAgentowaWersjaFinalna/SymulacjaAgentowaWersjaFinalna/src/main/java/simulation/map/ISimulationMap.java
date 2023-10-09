package simulation.map;

import simulation.position.IPosition;
import simulation.unit.IUnit;

public interface ISimulationMap {

    IUnit getUnit(IPosition position);
    int getMapSizeX();
    int getMapSizeY();
    IUnit[][] getMap();
    boolean putUnit(IUnit unit, IPosition position);
    IPosition getUnitPosition(IUnit unit);
    boolean destroyUnit(IUnit unit);

}
