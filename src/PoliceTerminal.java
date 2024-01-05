public class PoliceTerminal extends Terminal {

    private static final int processTime = 500;
    


    public PoliceTerminal(String name, boolean isWorking, boolean isAcceptingPersonalVehicles, boolean isAcceptingTrucks, boolean isAcceptingBusses) {
        super(name, isWorking, isAcceptingPersonalVehicles, isAcceptingTrucks, isAcceptingBusses);
    }




    public boolean process(Truck vehicle) {

        


        return true;
    }
    
    
}
