import java.io.Serializable;

public class Pet implements Serializable {
    protected int petId;
    protected String name;
    protected String species;
    protected int age;
    protected String breed;
    protected boolean adopted;

    public Pet(int petId, String name, String species, int age, String breed) {
        this.petId = petId;
        this.name = name;
        this.species = species;
        this.age = age;
        this.breed = breed;
        this.adopted = false;
    }

    public int getPetId() { return petId; }
    public String getName() { return name; }
    public String getSpecies() { return species; }
    public int getAge() { return age; }
    public String getBreed() { return breed; }
    public boolean isAdopted() { return adopted; }
    public void setAdopted(boolean adopted) { this.adopted = adopted; }

    public String toString() {
        return "[" + petId + "] " + name + " (" + species + ", " + age + " yrs, " + breed + ") - " +
                (adopted ? "Adopted" : "Available");
    }
}

class Dog extends Pet {
    public Dog(int petId, String name, int age, String breed) {
        super(petId, name, "Dog", age, breed);
    }
}

class Cat extends Pet {
    public Cat(int petId, String name, int age, String breed) {
        super(petId, name, "Cat", age, breed);
    }
}

class Bird extends Pet {
    public Bird(int petId, String name, int age, String breed) {
        super(petId, name, "Bird", age, breed);
    }
}