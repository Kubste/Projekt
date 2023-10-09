package simulation.unit;

import simulation.attack.Attack;
import simulation.attack.IAttack;
import simulation.map.ISimulationMap;

public class Tank extends AUnit {

    /**
     * Konstruktor obiektu jednostki klasy Tank
     * @param hp ilość punktów wytrzymałości jednostki
     * @param range zasięg jednostki
     * @param isA true -> przynależność jednostki do drużyny A; false -> przynależność jednostki do drużyny B
     * @param map obiekt klasy SimulationMap
     */
    public Tank(double hp, int range, boolean isA, ISimulationMap map) {
        super(hp, range, isA, map);
    }

    /**
     * Metoda odbierająca informację o zadanych jednostce obrażeniach, od obiektu pośredniczącego
     * @param attack obiekt pośredniczący w wymianie informacji o zadanych obrażeniach
     */
    @Override
    public void takeDamage(IAttack attack) {
        if(attack.getType() == 1) this.hp -= attack.getDamage();
        else if(attack.getType() == 2) this.hp -= 0.8 * attack.getDamage();
        else if(attack.getType() == 3) this.hp -= 1.2 * attack.getDamage();
    }

    /**
     * Metoda wysyłająca informację o obrażeniach zadawanych przez jednostkę
     * @return obiekt klasy Attack - pośredniczący w przekazywaniu informacji o zadanych obrażeniach
     */
    @Override
    public IAttack dealDamage() {
        return new Attack(30, 1);
    }

    /**
     * Metoda wyświetlająca jednostkę na ekranie
     * @return T -> jednostka należy do drużyny A; t -> jednostka należy do drużyny B
     */
    @Override
    public String toString() {
        if(this.isA) return "T";
        else return "t";
    }
}
