import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Adopter implements Serializable {
    private final int adopterId;
    private final String name;
    private final String contactInfo;
    private List<Integer> adoptedPetIds;

    public Adopter(int adopterId, String name, String contactInfo) {
        this.adopterId = adopterId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.adoptedPetIds = new ArrayList<>();
    }

    public int getAdopterId() { return adopterId; }
    public String getName() { return name; }
    public String getContactInfo() { return contactInfo; }
    public List<Integer> getAdoptedPetIds() { return adoptedPetIds; }
    public void adoptPet(int petId) { adoptedPetIds.add(petId); }

    public void setAdoptedPetIds(List<Integer> adoptedPetIds) {
        this.adoptedPetIds = adoptedPetIds;
    }

    public String toString() {
        return "[" + adopterId + "] " + name + " - Contact: " + contactInfo +
                ", Pets Adopted: " + adoptedPetIds;
    }
}