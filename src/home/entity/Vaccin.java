package home.entity;

public class Vaccin {
    private String antigene;
    private int age ;
    private String description;

    public Vaccin(String antigene, int age, String description) {
        this.antigene = antigene;
        this.age = age;
        this.description = description;
    }

    public String getAntigene() {
        return antigene;
    }

    public void setAntigene(String antigene) {
        this.antigene = antigene;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        String s =  antigene +" | " + age+" mois";
        if(!description.isEmpty()){
            s+=" | " + description;
        }
        return s;
    }
}
