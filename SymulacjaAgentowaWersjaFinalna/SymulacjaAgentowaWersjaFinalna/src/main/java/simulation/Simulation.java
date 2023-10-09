package simulation;

import simulation.map.ISimulationMap;
import simulation.map.creator.ISimulationMapCreator;
import simulation.position.IPosition;
import simulation.position.Position;
import simulation.unit.IUnit;
import simulation.unit.creator.IUnitCreator;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Simulation {

    private ISimulationMap map;
    private Random random;
    private List<IUnit> units;
    private int maxIteration;

    /**
     * Konstruktor obiektu symulacji
     * @param mapCreator obiekt klasy SimulationMapCreator - służący do utworzenia mapy symulacji
     * @param unitCreator obiekt klasy UnitCreator - służący do utworzenia obiektów jednostek
     * @param seed ziarno generatora pseudolosowego
     * @param maxIteration maksymalna liczba iteracji symulacji
     */
    public Simulation(ISimulationMapCreator mapCreator, IUnitCreator unitCreator, long seed, int maxIteration) {
        this.map = mapCreator.createMap();
        this.random = new Random(seed);
        this.units = unitCreator.createUnits(map);
        this.maxIteration = maxIteration;

        for (int i = 0; i < units.size(); i++) {
            IPosition position = new Position(random.nextInt(map.getMapSizeX()), random.nextInt(map.getMapSizeY()));
            while (!map.putUnit(units.get(i), position)) {
                position.setX(random.nextInt(map.getMapSizeX()));
                position.setY(random.nextInt(map.getMapSizeY()));
            }
        }
    }

    /**
     * Metoda niszcząca wszystkie "martwe" jednostki na koniec iteracji, wykorzystująca metodę destroyUnit() z klasy SimulationMap
     */
    private void destroyUnits() {
        Iterator<IUnit> iterator = units.iterator();
        while (iterator.hasNext()) {
            IUnit unit = iterator.next();
            if (unit.getSimulationMap().destroyUnit(unit)) iterator.remove();
        }
    }

    /**
     * Metoda zwracająca informację, czy jakakolwiek jednostka z drużyny A "jest żywa"
     * @return true -> co najmniej jedna jednostka z drużyny A "jest żywa"; false -> wszystkie jednostki z drużyny A "są martwe"
     */
    public boolean isAnyoneAliveA() {
        int i = 0;
        for (IUnit unit : units) if (unit.getIsA()) i++;
        if (i > 0) return true;
        else return false;
    }

    /**
     * Metoda zwracająca informację, czy jakakolwiek jednostka z drużyny B "jest żywa"
     * @return true -> co najmniej jedna jednostka z drużyny B "jest żywa"; false -> wszystkie jednostki z drużyny B "są martwe"
     */
    public boolean isAnyoneAliveB() {
        int i = 0;
        for (IUnit unit : units) if (!unit.getIsA()) i++;
        if (i > 0) return true;
        else return false;
    }

    /**
     * Metoda wyświetlająca mapę symulacji na ekranie
     */
    public void printMap() {
        IUnit[][] map = this.map.getMap();

        for (int i = map[0].length - 1; i >= 0; i--) {
            for (int j = 0; j < map.length; j++) {
                if (map[j][i] == null) {
                    System.out.print("# ");
                } else {
                    System.out.print(map[j][i].toString());
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Metoda przeprowadzająca przebieg symulacji:
     * 1. Wyświetlana jest mapa symulacji po ustawieniu wszystkich jednostek
     * 2. Dla każdej jednostki wykonywana jest metoda doAction()
     * 3. Usuwane są "martwe" jednostki
     * 4. Jeśli wszystkie jednostki z jednej drużyny "są martwe" - symulacja kończy się
     * @return iteration - liczba iteracji które odtworzyła metoda
     */
    public int runSimulation() {
        int iteration = 0;
        System.out.println("Ekran stanu początkowego: ");
        this.printMap();
        System.out.println();
        for (; iteration < maxIteration - 1; iteration++) {
            for (IUnit unit : units) {
                unit.doAction();
            }

            this.destroyUnits();

            if (!this.isAnyoneAliveA() || !this.isAnyoneAliveB()) {
                System.out.println("Iteracja: " + (iteration + 1));
                this.printMap();
                System.out.println();
               return iteration;
            }

            System.out.println("Iteracja: " + (iteration + 1));
            this.printMap();
            System.out.println();
        }
        return iteration - 1;
    }
}