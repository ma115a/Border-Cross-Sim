import java.util.Random;


public class Document {

    private static final double validDocumentBound = 0.03;
    
    private String ownerName;
    private String ownerLastName;
    private int documentId;
    private boolean isValid;

    public Document() {

    }

    public Document(String ownerName, String ownerLastName) {
        this.ownerName = ownerName;
        this.ownerLastName = ownerLastName;
        this.isValid = true;
        Random rand = new Random();
        this.documentId = rand.nextInt(1_000_000);
        double probability = rand.nextDouble();
        // System.out.println(probability);
        if(probability <= validDocumentBound) {
            this.isValid = false;
        }
    }

    public Document(String ownerName, String ownerLastName, int documentId) {
        this.ownerName = ownerName;
        this.ownerLastName = ownerLastName;
        this.documentId = documentId;
        this.isValid = true;
        Random rand = new Random();
        double probability = rand.nextDouble();
        if(probability <= validDocumentBound) {
            this.isValid = false;
        }
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getOwnerLastName() {
        return this.ownerLastName;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public int getId() {
        return this.documentId;
    }

    @Override
    public String toString() {
        return this.ownerName + " " + this.ownerLastName + " Document ID: " + this.documentId +" Valid: " + this.isValid;
    }

}
