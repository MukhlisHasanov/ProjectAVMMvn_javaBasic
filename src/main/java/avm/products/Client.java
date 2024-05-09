package avm.products;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Andrey Hein
 * @version Apr-2024
 */
public class Client {
    Integer id;
    String name;
    int age;
    float wallet;
    //private static int idCounter = 0;

    public Client(String name, int age, float wallet) {
        //this.id = ++idCounter;
        this.name = name;
        this.age = age;
        this.wallet = wallet;
    }

    public Client(Integer id, String name, int age, float wallet) {
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public void update(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return  "[" + name +
                ", id: " + id +
                ", age: " + age + "]";
    }
}