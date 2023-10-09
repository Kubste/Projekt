package simulation.map.creator;

import simulation.map.ISimulationMap;
import simulation.map.SimulationMap;

public class SimulationMapCreator implements ISimulationMapCreator {

    private int mapSizeX;
    private int mapSizeY;

    /**
     * Konstruktor obiektu tworzącego mapę symulacji
     * @param mapSizeX rozmiar mapy w osi X
     * @param mapSizeY rozmiar mapy w osi Y
     */
    public SimulationMapCreator(int mapSizeX, int mapSizeY) {
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;
    }

    /**
     * Metoda tworząca mapę symulacji
     * @return SimulationMap - obiekt mapy symulacji
     */
    @Override
    public ISimulationMap createMap() {
        return new SimulationMap(mapSizeX, mapSizeY);
    }
}
