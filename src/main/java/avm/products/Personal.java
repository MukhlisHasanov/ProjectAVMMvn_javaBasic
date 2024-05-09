package avm.products;

/**
 * AIT-TR, cohort 42.1, Java Basic, Project AVM/General
 * @author Andrey Hein
 * @version May-2024
 */
public class Personal {
    Integer id;
    String name;
    String department;

    public Personal(String name, String department) {
        this.name = name;
        this.department = department;
    }

    public Personal(Integer id, String name, String department) {
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void update(String name, String department) {
        this.name = name;
        this.department = department;
    }
}
