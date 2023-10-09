package simulation.attack;

public class Attack implements IAttack {

    private double damage;
    private int type;

    /**
     * Konstruktor obiektu, pośredniczącego w wymianie informacji o zadawanych obrażeniach, pomiędzy klasami jednostek
     * @param damage wartość obrażeń zadawanych przez jednostkę
     * @param type typ jednostki zadającej obrażenia
     */
    public Attack(double damage, int type) {
        this.damage = damage;
        this.type = type;
    }

    /**
     * Metoda pobierająca wartość zadawanych obrażeń od obiektu pośredniczącego
     * @return damage - wartość zadawanych obrażeń
     */
    @Override
    public double getDamage() {
        return this.damage;
    }

    /**
     * Metoda przypisująca wartości parametrów obiektu pośredniczącego w zadawaniu obrażeń
     * @param damage wartość zadawanych obrażeń
     * @param type typ jednostki zadającej obrażenia
     */
    @Override
    public void setDamage(double damage, int type) {
        this.damage = damage;
        this.type = type;
    }

    /**
     * Metoda zwracająca typ jednostki, która zadaje obrażenia, odpowiednio:
     * 1 -> Tank
     * 2 -> Infantry
     * 3 -> Artillery
     * @return type - typ jednostki zadającej obrażenia
     */
    @Override
    public int getType() {
        return this.type;
    }
}
