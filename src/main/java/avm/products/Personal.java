package avm.products;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Andrey Hein
 * @version May-2024
 */
public class Personal {
    Integer id;
    String name;
    PersonalState department;

    public Personal(String name, PersonalState department) {
        this.name = name;
        this.department = department;
    }

    public Personal(Integer id, String name, PersonalState department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonalState getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return  "[" + name +
                ", id: " + id +
                ", department: " + department +"]";
    }

    public void update(String name, PersonalState department) {
        this.name = name;
        this.department = department;
    }

    @Override
    public String toString() {
        return  "[id: " + id +
                ", " + name +
                ", department: " + department +"]";
    }
}