package passengers;
import java.io.Serializable;
import java.util.Random;


public class Document implements Serializable {

    private static final double validDocumentBound = 0.03;
    
    private int documentId;
    private boolean isValid;



    public Document() {
        this.isValid = true;
        Random rand = new Random();
        this.documentId = rand.nextInt(1_000_000);
        double probability = rand.nextDouble();
        // System.out.println(probability);
        if(probability <= validDocumentBound) {
            this.isValid = false;
        }
    }

    public Document(int documentId, boolean isValid) {
        this.documentId = documentId;
        this.isValid = isValid;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public int getId() {
        return this.documentId;
    }

    @Override
    public String toString() {
        return "Document ID: " + this.documentId + ", valid: " + this.isValid;
    }

}
