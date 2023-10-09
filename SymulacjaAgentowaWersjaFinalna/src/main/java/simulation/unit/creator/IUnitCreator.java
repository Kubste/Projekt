package simulation.unit.creator;

import simulation.map.ISimulationMap;
import simulation.unit.IUnit;

import java.util.List;

public interface IUnitCreator {

    List<IUnit> createUnits(ISimulationMap map);

}
