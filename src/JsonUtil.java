import java.io.*;
import java.util.*;

public class JsonUtil {
    private static final String FILE_NAME = "records.json";

    public static void saveData(PetAdoptionCenter center) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            writer.println("{");
            writer.println("  \"pets\": [");
            List<Pet> pets = new ArrayList<>(center.getPets().values());
            for (int i = 0; i < pets.size(); i++) {
                Pet p = pets.get(i);
                writer.print("    { \"petId\": " + p.getPetId() +
                        ", \"name\": \"" + p.getName() + "\"" +
                        ", \"species\": \"" + p.getSpecies() + "\"" +
                        ", \"age\": " + p.getAge() +
                        ", \"breed\": \"" + p.getBreed() + "\"" +
                        ", \"adopted\": " + p.isAdopted() + " }");
                writer.println(i < pets.size() - 1 ? "," : "");
            }
            writer.println("  ],");
            writer.println("  \"adopters\": [");
            List<Adopter> adopters = new ArrayList<>(center.getAdopters().values());
            for (int i = 0; i < adopters.size(); i++) {
                Adopter a = adopters.get(i);
                writer.print("    { \"adopterId\": " + a.getAdopterId() +
                        ", \"name\": \"" + a.getName() + "\"" +
                        ", \"contactInfo\": \"" + a.getContactInfo() + "\"" +
                        ", \"adoptedPetIds\": " + a.getAdoptedPetIds() + " }");
                writer.println(i < adopters.size() - 1 ? "," : "");
            }
            writer.println("  ]");
            writer.println("}");
        } catch (IOException e) {
            System.out.println("Error saving records: " + e.getMessage());
        }
    }

    public static void loadData(PetAdoptionCenter center) {
        try (Scanner scanner = new Scanner(new File("records.json"))) {
            StringBuilder jsonBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                jsonBuilder.append(scanner.nextLine());
            }

            String json = jsonBuilder.toString().replaceAll("\\s", "");
            if (json.contains("\"pets\":[")) {
                String petsArray = json.split("\"pets\":\\[")[1].split("]")[0];
                String[] petObjects = petsArray.split("},\\{");

                for (int i = 0; i < petObjects.length; i++) {
                    String p = petObjects[i].trim();
                    if (i == 0 && p.startsWith("{")) p = p.substring(1);
                    if (i == petObjects.length - 1 && p.endsWith("}")) p = p.substring(0, p.length() - 1);

                    String[] fields = p.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                    int id = 0, age = 0;
                    String name = "", species = "", breed = "";
                    boolean adopted = false;

                    for (String field : fields) {
                        String[] kv = field.split(":", 2);
                        if (kv.length < 2) continue;

                        String key = kv[0].replace("\"", "").trim();
                        String value = kv[1].replace("\"", "").trim();

                        switch (key) {
                            case "petId" -> id = Integer.parseInt(value);
                            case "name" -> name = value;
                            case "species" -> species = value;
                            case "age" -> age = Integer.parseInt(value);
                            case "breed" -> breed = value;
                            case "adopted" -> adopted = Boolean.parseBoolean(value);
                        }
                    }

                    Pet pet = switch (species.toLowerCase()) {
                        case "dog" -> new Dog(id, name, age, breed);
                        case "cat" -> new Cat(id, name, age, breed);
                        case "bird" -> new Bird(id, name, age, breed);
                        default -> new Pet(id, name, species, age, breed);
                    };

                    pet.setAdopted(adopted);
                    center.addPet(pet);
                    center.setNextPetId(Math.max(center.getNextPetId(), id + 1));
                }
            }

            if (json.contains("\"adopters\":[")) {
                String adoptersArray = json.split("\"adopters\":\\[")[1].split("]")[0];
                String[] adopterObjects = adoptersArray.split("},\\{");

                for (int i = 0; i < adopterObjects.length; i++) {
                    String a = adopterObjects[i].trim();
                    if (i == 0 && a.startsWith("{")) a = a.substring(1);
                    if (i == adopterObjects.length - 1 && a.endsWith("}")) a = a.substring(0, a.length() - 1);

                    String[] fields = a.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                    int adopterId = 0;
                    String name = "", contactInfo = "";
                    List<Integer> adoptedIds = new ArrayList<>();

                    for (String field : fields) {
                        String[] kv = field.split(":", 2);
                        if (kv.length < 2) continue;

                        String key = kv[0].replace("\"", "").trim();
                        String value = kv[1].trim();

                        switch (key) {
                            case "adopterId" -> adopterId = Integer.parseInt(value);
                            case "name" -> name = value.replace("\"", "");
                            case "contactInfo" -> contactInfo = value.replace("\"", "");
                            case "adoptedPetIds" -> {
                                value = value.replace("[", "").replace("]", "");
                                if (!value.isEmpty()) {
                                    for (String idStr : value.split(",")) {
                                        adoptedIds.add(Integer.parseInt(idStr.trim()));
                                    }
                                }
                            }
                        }
                    }
                    if (!name.isEmpty()) {
                        Adopter adopter = new Adopter(adopterId, name, contactInfo);
                        adopter.setAdoptedPetIds(adoptedIds);
                        center.addAdopter(adopter);
                        center.setNextAdopterId(Math.max(center.getNextAdopterId(), adopterId + 1));
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("No previous data found or error loading: " + e.getMessage());
        }
    }

}
