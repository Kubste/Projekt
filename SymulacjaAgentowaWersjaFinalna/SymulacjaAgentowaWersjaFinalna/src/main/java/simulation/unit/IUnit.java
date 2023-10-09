package simulation.unit;

import simulation.attack.IAttack;
import simulation.map.ISimulationMap;
import simulation.position.IPosition;

import java.util.List;

public interface IUnit {

    void takeDamage(IAttack attack);
    IAttack dealDamage();
    void attack(IUnit target);
    boolean isAlive();
    boolean isFriendly(IUnit unit);
    void move();
    ISimulationMap getSimulationMap();
    void setSimulationMap(ISimulationMap map);
    boolean getIsA();
    IUnit searchForUnit();
    void doAction();
    List<IPosition> getSurroundings();

}
