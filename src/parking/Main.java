package parking;

import model.ParkingSlot;
import model.Vehicle;
import model.VehicleType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;
import java.util.function.BiConsumer;

/**
 * Created by shashwat on 7/6/16.
 */
public class Main {

    // Initializing all the global variables
    private static int no_of_slots;
    private static PriorityQueue<ParkingSlot> slots;
    private static int occupancy = 0;
    private static final VehicleType[] canAccommodate = {VehicleType.TRUCK, VehicleType.CAR, VehicleType.BUS};
    private static HashMap<String,ParkingSlot> idx_regNum;
    private static HashMap<String,List<ParkingSlot>> idx_color, idx_model, idx_manufacturer;
    private static HashMap<VehicleType,List<ParkingSlot>> idx_vehicleType;
    public static Semaphore lockOnSlots = new Semaphore(1, true);                 // Restricts only 1 access to the queue slots.


    public static int getNo_of_slots() {
        return no_of_slots;
    }

    public static ParkingSlot nextFreeSlot() {
        return slots.peek();
    }

    public static ParkingSlot getFreeSlot(){
        // TO-DO: Check if the parking slot is full
        return slots.poll();
    }

    public static boolean addFreedSlotToQueue(ParkingSlot p){
        return slots.add(p);
    }

    public static int getOccupancy() {
        return occupancy;
    }

    public static void setOccupancy(int occupancy) {
        Main.occupancy = occupancy;
    }

    public static void addToIndexes(Vehicle v, ParkingSlot p){
        idx_regNum.put(v.getRegistrationNumber(), p);
        Utils.appendToListInMap(idx_color, v.getColor(), p);
        Utils.appendToListInMap(idx_model, v.getModel(), p);
        Utils.appendToListInMap(idx_manufacturer, v.getManufacturer(), p);
        Utils.appendToListInMap(idx_vehicleType, v.getvType(), p);
    }

    public static void removeFromIndexes(Vehicle v, ParkingSlot p){
        // TO-DO: Check for existence and then delete
        idx_regNum.remove(v.getRegistrationNumber(), p);
        Utils.removeFromListInMap(idx_color, v.getColor(), p);
        Utils.removeFromListInMap(idx_model, v.getModel(), p);
        Utils.removeFromListInMap(idx_manufacturer, v.getManufacturer(), p);
        Utils.removeFromListInMap(idx_vehicleType, v.getvType(), p);
    }

    public static ParkingSlot locateVehicleOnRegNum(String regNum){
        return idx_regNum.getOrDefault(regNum, null);
    }

    public static List<ParkingSlot> locateVehicleOnType(VehicleType vType){
        return idx_vehicleType.getOrDefault(vType, null);
    }

    public static List<ParkingSlot> locateVehicleOnColor(String color){
        return idx_color.getOrDefault(color, null);
    }

    public static List<ParkingSlot> locateVehicleOnModel(String model){
        return idx_model.getOrDefault(model, null);
    }

    public static List<ParkingSlot> locateVehicleOnManufacturer(String man){
        return idx_manufacturer.getOrDefault(man, null);
    }


    public static void printPakingLotOccupancy(){
        System.out.println("\n");
        // Print occupied slots
        idx_regNum.forEach(new BiConsumer<String, ParkingSlot>() {
            @Override
            public void accept(String s, ParkingSlot parkingSlot) {
                System.out.println(parkingSlot.getId() + " is occupied by " + s);
            }
        });
        // Print free slots
        Iterator<ParkingSlot> i = slots.iterator();
        while(i.hasNext()){
            ParkingSlot p = i.next();
            Vehicle v = p.getOccupiedBy();
            if(v == null) System.out.println(p.getId() + " is empty.");
            else System.out.println(p.getId() + " is occupied by " + v.getRegistrationNumber());
        }
        System.out.println("\n");
    }

    public static void main(String args[]) throws IOException{
        String usage = "Usage: java parking.Main [NoOfParkingSlots] [OperatorName]";
        if(args.length != 2) {
            System.out.println(usage);
            System.exit(1);
        }
        /*
        *  Begin initializing the parking lot.
        * */
        no_of_slots = Integer.parseInt(args[0].trim());
        slots = new PriorityQueue<>(no_of_slots, new ParkingSlotComparator());
        String operatorName = args[1];
        // Initializing each parking slot with all possibilities of accomodation
        for(int i = 0; i < no_of_slots; i++){
            slots.add(new ParkingSlot(i,canAccommodate));
        }
        idx_color = new HashMap<>();
        idx_model = new HashMap<>();
        idx_vehicleType = new HashMap<>();
        idx_manufacturer = new HashMap<>();
        idx_regNum = new HashMap<>();
        /*
        * Create operators to start operating on the parking lot
        * */
        Operator op1 = new Operator();
        op1.setName(operatorName);
        op1.start();
    }



}