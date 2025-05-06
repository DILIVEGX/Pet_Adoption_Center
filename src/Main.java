import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PetAdoptionCenter center = new PetAdoptionCenter();
        JsonUtil.loadData(center);

        boolean running = true;
        while (running) {
            System.out.println("\n🐾 Pet Adoption Center 🐾");
            System.out.println("1. Add Pet");
            System.out.println("2. Show Available Pets");
            System.out.println("3. Register Adopter");
            System.out.println("4. Adopt a Pet");
            System.out.println("5. Search Pets by Species");
            System.out.println("6. Show Adopters and Adopted Pets");
            System.out.println("7. Exit and Save");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter species (Dog/Cat/Bird): ");
                    String species = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt(); scanner.nextLine();
                    System.out.print("Enter breed: ");
                    String breed = scanner.nextLine();

                    int petId = center.getNextPetId();
                    Pet pet = switch (species.toLowerCase()) {
                        case "dog" -> new Dog(petId, name, age, breed);
                        case "cat" -> new Cat(petId, name, age, breed);
                        case "bird" -> new Bird(petId, name, age, breed);
                        default -> new Pet(petId, name, species, age, breed);
                    };
                    center.addPet(pet);
                    center.setNextPetId(petId + 1);
                    System.out.println("Pet added!");
                    break;
                case 2:
                    center.showAvailablePets();
                    break;
                case 3:
                    System.out.print("Enter adopter name: ");
                    String adopterName = scanner.nextLine();
                    System.out.print("Enter contact info: ");
                    String contactInfo = scanner.nextLine();
                    center.registerAdopter(adopterName, contactInfo);
                    break;
                case 4:
                    System.out.print("Enter pet ID: ");
                    int pId = scanner.nextInt();
                    System.out.print("Enter adopter ID: ");
                    int aId = scanner.nextInt();
                    center.adoptPet(pId, aId);
                    break;
                case 5:
                    System.out.print("Enter species: ");
                    String s = scanner.nextLine();
                    center.searchBySpecies(s);
                    break;
                case 6:
                    center.showAdoptersAndAdoptedPets();
                    break;
                case 7:
                    JsonUtil.saveData(center);
                    System.out.println("Data saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }
}