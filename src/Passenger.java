public class Passenger {

    private Document document;

    public Passenger() {

    }

    public Passenger(String name, String lastName) {
        this.document = new Document(name, lastName);
    }
    
    public Passenger(String name, String lastName, int documentId) {
        this.document = new Document(name, lastName, documentId);
    }


    public String getName() {
        return this.document.getOwnerName();
    }

    public String getLastName() {
        return this.document.getOwnerLastName();
    }

    public void setName(String name) {
        this.document.setOwnerName(name);
    }

    public void setLastName(String lastName) {
        this.document.setOwnerLastName(lastName);
    }

    public int getDocumentId() {
        return this.document.getId();
    }

    public boolean isDocumentValid() {
        return this.document.isValid();
    }

    @Override
    public String toString() {
        return "\nPassenger: " + this.document;
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
