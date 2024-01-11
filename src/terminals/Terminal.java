package terminals;
public abstract class Terminal {



    private boolean isFree;

    private boolean isAcceptingPersonalVehicles;
    private boolean isAcceptingTrucks;
    private boolean isAcceptingBusses;

    private volatile boolean isWorking;


    private String name;


    public Terminal(String name, boolean isWorking, boolean isAcceptingPersonalVehicles, boolean isAcceptingTrucks, boolean isAcceptingBusses) {
        this.name = name;
        this.isWorking = isWorking;
        this.isAcceptingPersonalVehicles = isAcceptingPersonalVehicles;
        this.isAcceptingTrucks = isAcceptingTrucks;
        this.isAcceptingBusses = isAcceptingBusses;
        this.isFree = true;
    }



    public boolean isFree() {
        return this.isFree;
    }

    public boolean isWorking() {
        return this.isWorking;
    }
 
    public boolean isAcceptingPersonalVehicles() {
        return this.isAcceptingPersonalVehicles;
    }

    public boolean isAcceptingTrucks() {
        return this.isAcceptingTrucks;
    }

    public boolean isAcceptingBusses() {
        return this.isAcceptingBusses;
    }

    public void occupy() {
        this.isFree = false;
    }

    public void free() {
        this.isFree = true; 
    }

    public void setWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public String getTerminalName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
}
