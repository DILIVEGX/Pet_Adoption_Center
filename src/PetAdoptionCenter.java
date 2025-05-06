import java.util.*;

public class PetAdoptionCenter {
    private final Map<Integer, Pet> pets = new HashMap<>();
    private final Map<Integer, Adopter> adopters = new HashMap<>();
    private int nextPetId = 1;
    private int nextAdopterId = 1;

    public void addPet(Pet pet) {
        pets.put(pet.getPetId(), pet);
    }

    public void registerAdopter(String name, String contactInfo) {
        Adopter adopter = new Adopter(nextAdopterId++, name, contactInfo);
        adopters.put(adopter.getAdopterId(), adopter);
        System.out.println("Adopter registered with ID: " + adopter.getAdopterId());
    }

    public void adoptPet(int petId, int adopterId) {
        Pet pet = pets.get(petId);
        Adopter adopter = adopters.get(adopterId);
        if (pet == null || adopter == null) {
            System.out.println("Invalid pet or adopter ID.");
        } else if (pet.isAdopted()) {
            System.out.println("This pet has already been adopted.");
        } else {
            pet.setAdopted(true);
            adopter.adoptPet(petId);
            System.out.println(adopterId + " has adopted " + pet.getName() + "!");
        }
    }

    public void showAvailablePets() {
        pets.values().stream()
                .filter(p -> !p.isAdopted())
                .forEach(System.out::println);
    }

    public void searchBySpecies(String species) {
        pets.values().stream()
                .filter(p -> p.species.equalsIgnoreCase(species) && !p.isAdopted())
                .forEach(System.out::println);
    }

    public void showAdoptersAndAdoptedPets() {
        if (adopters.isEmpty()) {
            System.out.println("No adopters found.");
            return;
        }

        for (Adopter adopter : adopters.values()) {
            System.out.println("Adopter: " + adopter.getName());
            System.out.println("Contact Info: " + adopter.getContactInfo());
            System.out.println("Adopted Pets:");

            if (adopter.getAdoptedPetIds().isEmpty()) {
                System.out.println("  No pets adopted yet.");
            } else {
                for (int petId : adopter.getAdoptedPetIds()) {
                    Pet pet = pets.get(petId);
                    if (pet != null) {
                        System.out.println("  Pet Name: " + pet.getName() + ", Species: " + pet.getSpecies() + ", Age: " + pet.getAge() + " years, Adopted: " + pet.isAdopted());
                    }
                }
            }
            System.out.println();
        }
    }

    public Map<Integer, Pet> getPets() {
        return pets;
    }

    public Map<Integer, Adopter> getAdopters() {
        return adopters;
    }

    public int getNextPetId() {
        return nextPetId;
    }

    public int getNextAdopterId() {
        return nextAdopterId;
    }

    public void setNextPetId(int id) {
        nextPetId = id;
    }

    public void setNextAdopterId(int id) {
        nextAdopterId = id;
    }

    public void addAdopter(Adopter adopter) {
        adopters.put(adopter.getAdopterId(), adopter);
    }
}
