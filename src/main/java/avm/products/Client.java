package avm.products;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Andrey Hein
 * @version Apr-2024
 */
public class Client {
    int id, age;
    String name;
    private static int idCounter = 0;

    public Client(String name, int age) {
        this.id = ++idCounter;
        this.name = name;
        this.age = age;
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

//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }

    @Override
    public String toString() {
        return  "[" + name +
                ", id: " + id +
                ", age: " + age + "]";
    }
}