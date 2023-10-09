package simulation.unit;

import simulation.attack.Attack;
import simulation.attack.IAttack;
import simulation.map.ISimulationMap;

public class Artillery extends AUnit {

    /**
     * Konstruktor obiektu jednostki klasy Artillery
     * @param hp ilość punktów wytrzymałości jednostki
     * @param range zasięg jednostki
     * @param isA true -> przynależność jednostki do drużyny A; false -> przynależność jednostki do drużyny B
     * @param map obiekt klasy SimulationMap
     */
    public Artillery(double hp, int range, boolean isA, ISimulationMap map) {
        super(hp, range, isA, map);
    }

    /**
     * Metoda odbierająca informację o zadanych jednostce obrażeniach, od obiektu pośredniczącego
     * @param attack obiekt pośredniczący w wymianie informacji o zadanych obrażeniach
     */
    @Override
    public void takeDamage(IAttack attack) {
        if(attack.getType() == 1) this.hp -= 0.8 * attack.getDamage();
        else if(attack.getType() == 2) this.hp -= 1.2 * attack.getDamage();
        else if(attack.getType() == 3) this.hp -= attack.getDamage();
    }

    /**
     * Metoda wysyłająca informację o obrażeniach zadawanych przez jednostkę
     * @return obiekt klasy Attack - pośredniczący w przekazywaniu informacji o zadanych obrażeniach
     */
    @Override
    public IAttack dealDamage() {
        return new Attack(35, 3);
    }

    /**
     * Metoda wyświetlająca jednostkę na ekranie
     * @return A -> jednostka należy do drużyny A; a -> jednostka należy do drużyny B
     */
    @Override
    public String toString() {
        if(this.isA) return "A";
        else return "a";
    }
}
