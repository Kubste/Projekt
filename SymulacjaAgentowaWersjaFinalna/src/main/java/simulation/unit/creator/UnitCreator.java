package simulation.unit.creator;

import simulation.map.ISimulationMap;
import simulation.unit.Artillery;
import simulation.unit.IUnit;
import simulation.unit.Infantry;
import simulation.unit.Tank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnitCreator implements IUnitCreator {

    private int numberTankA;
    private int numberTankB;
    private int numberInfantryA;
    private int numberInfantryB;
    private int numberArtilleryA;
    private int numberArtilleryB;

    /**
     * Konstruktor obiektu tworzącego obiekty jednostek
     * @param numberTankA ilość obiektów jednostki Tank w drużynie A
     * @param numberTankB ilość obiektów jednostki Tank w drużynie B
     * @param numberInfantryA ilość obiektów jednostki Infantry w drużynie A
     * @param numberInfantryB ilość obiektów jednostki Infantry w drużynie B
     * @param numberArtilleryA ilość obiektów jednostki Artillery w drużynie A
     * @param numberArtilleryB ilość obiektów jednostki Artillery w drużynie B
     */
    public UnitCreator(int numberTankA, int numberTankB, int numberInfantryA, int numberInfantryB, int numberArtilleryA, int numberArtilleryB) {
        this.numberTankA = numberTankA;
        this.numberTankB = numberTankB;
        this.numberInfantryA = numberInfantryA;
        this.numberInfantryB = numberInfantryB;
        this.numberArtilleryA = numberArtilleryA;
        this.numberArtilleryB = numberArtilleryB;
    }

    /**
     * Metoda tworząca obiekty jednostek, oraz dodająca je do listy jednostek
     * @param map obiekt klasy SimulationMap przekazywany każdemu obiektowi jednostki
     * @return units - lista utworzonych obiektów jednostek
     */
    @Override
    public List<IUnit> createUnits(ISimulationMap map) {
        List<IUnit> units = new ArrayList<>();

        for(int i = 0; i < numberTankA; i++) units.add(new Tank(100, 2, true, map));
        for(int i = 0; i < numberTankB; i++) units.add(new Tank(100, 2, false, map));
        for(int i = 0; i < numberInfantryA; i++) units.add(new Infantry(80, 1, true, map));
        for(int i = 0; i < numberInfantryB; i++) units.add(new Infantry(80, 1, false, map));
        for(int i = 0; i < numberArtilleryA; i++) units.add(new Artillery(70, 3, true, map));
        for(int i = 0; i < numberArtilleryB; i++) units.add(new Artillery(70, 3, false, map));

        Collections.shuffle(units);

        return units;
    }
}
