package passengers;

import java.io.Serializable;

public class Passenger implements Serializable{

    private Document document;
    private String name;
    private String lastName;

    public Passenger() {

    }

    public Passenger(String name, String lastName) {
        this.document = new Document();
        this.name = name;
        this.lastName = lastName;
    }

    public Passenger(String name, String lastName, int documentId, boolean isValid) {
        this.document = new Document(documentId, isValid);
        this.name = name;
        this.lastName = lastName;
    }
    

    public String getName() {
        return this.name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getFullName() {
        return this.name + " " + this.lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getDocumentId() {
        return this.document.getId();
    }

    public boolean isDocumentValid() {
        return this.document.isValid();
    }

    @Override
    public String toString() {
        return this.name + " " + this.lastName + " " + this.document;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj != null && (this.getClass() == obj.getClass())) {
            Passenger other = (Passenger) obj;
            if(this.getName().equals(other.getName()) && this.getLastName().equals(other.getLastName()) && this.getDocumentId() == other.getDocumentId()) {
                return true;
            }
        }
        return false;
    }
    
}
