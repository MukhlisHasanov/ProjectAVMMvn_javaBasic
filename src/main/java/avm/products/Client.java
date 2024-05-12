package avm.products;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Andrey Hein
 * @version Apr-2024
 */
public class Client {
    float wallet;
    int id, age;
    String name;
    private static int idCounter = 0;

    public Client(String name, int age, float wallet) {
        this.id = ++idCounter;
        this.name = name;
        this.age = age;
        this.wallet = wallet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    @Override
    public String toString() {
        return  "[" + name +
                ", id: " + id +
                ", age: " + age +
                ", wallet: " + wallet +"]";
    }
}