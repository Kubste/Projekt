package simulation.attack;

public interface IAttack {

    void setDamage(double damage, int type);
    double getDamage();
    int getType();

}
