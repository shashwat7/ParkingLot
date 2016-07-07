package parking;

import exception.InvalidVehicleException;
import exception.ParkingFullException;
import exception.VehicleNotFoundException;
import model.ParkingSlot;
import model.Vehicle;
import model.VehicleType;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static parking.Main.*;

/**
 * Created by srastogi on 7/7/2016.
 */
public class Operator extends Thread{

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public void run(){
        /*
        * Handling user input/output.
        * */
        while(true){
            Utils.printMenu();
            switch (Utils.getUserChoice(br)){
                case 1 : {
                    System.out.println("Enter the vehicle details:\n");
                    System.out.println("[Type],[Manufacturer],[Model],[RegistrationNumber],[Color]");
                    // TO-DO: Make things optional
                    try{
                        String in = br.readLine();
                        Vehicle v = Utils.createVehicleObj(in);
                        if(v == null) throw new InvalidVehicleException("Invalid Entry");
                        Main.lockOnSlots.acquire(); // Wait for exclusive access to parking slot
                        ParkingSlot p = getFreeSlot(); // TO-DO: Check whether p can accommodate vehicle type of v
                        if(p != null){
                            Utils.parkVehicle(v,p);
                            Main.addToIndexes(v,p);
                            Main.setOccupancy(getOccupancy() + 1);
                        } else throw new ParkingFullException("No more space left in parking slot.");
                    } catch (Exception e){
                        System.out.println("Unable to park vehicle, ERROR: " + e);
                        break;
                    }
                    finally {
                        Main.lockOnSlots.release(); // Finish exclusive access
                    }
                    System.out.println("Park successful");
                    break;
                }
                case 2: {
                    System.out.println("Enter vehicle's registration number");
                    try{
                        String regNum = br.readLine();
                        Main.lockOnSlots.acquire();
                        ParkingSlot pos = locateVehicleOnRegNum(regNum);
                        if(pos != null){
                            Main.removeFromIndexes(pos.getOccupiedBy(), pos);
                            Utils.unparkVehicle(pos.getOccupiedBy(), pos);
                            Main.addFreedSlotToQueue(pos);
                            Main.setOccupancy(getOccupancy() - 1);
                        } else throw new VehicleNotFoundException("Vehicle number " + regNum + " not found.");
                    } catch(Exception e){
                        System.out.println("Error in un-parking the vehicle.\n");
                        System.out.println("Error: " + e);
                    } finally {
                        Main.lockOnSlots.release();
                    }
                    System.out.println("Un-park successful");
                    break;
                }
                case 3: {
                    System.out.println(getOccupancy() + " slots occupied out of " + Main.getNo_of_slots());
                    Main.printPakingLotOccupancy();
                    break;
                }
                case 4: {
                    System.out.println("Search -t [VehicleType] -r [ListOfCommaSeparatedRegistrationNumbers] -cl [Color] -m [Model] -co [ManufacturingCompany]");
                    // TO-DO: current implementation takes the union, change it to intersection later.
                    try{
                        Set<ParkingSlot> listOfSlots = new HashSet<>();
                        Map<String,String> m = Utils.readSearchParameters(br.readLine());
                        m.forEach(new BiConsumer<String, String>() {
                            @Override
                            public void accept(String s1, String s2) {
                                System.out.println(s1+" -> "+s2);
                                switch (s1){
                                    case "t": {
                                        switch(s2.toLowerCase()){
                                            case "car": listOfSlots.addAll(Main.locateVehicleOnType(VehicleType.CAR));
                                                break;
                                            case "bus": listOfSlots.addAll(Main.locateVehicleOnType(VehicleType.BUS));
                                                break;
                                            case "truck": listOfSlots.addAll(Main.locateVehicleOnType(VehicleType.TRUCK));
                                                break;
                                            default: System.out.println("Vehicle type can only be can/truck/bus.");
                                        }
                                        break;
                                    }
                                    case "r": {
                                        for(String regNo : s2.split("\\,")){
                                            listOfSlots.add(locateVehicleOnRegNum(regNo));
                                        }
                                        break;
                                    }
                                    case "m": {
                                        listOfSlots.addAll(locateVehicleOnModel(s2));
                                        break;
                                    }
                                    case "cl": {
                                        listOfSlots.addAll(locateVehicleOnColor(s2));
                                        break;
                                    }
                                    case "co": {
                                        listOfSlots.addAll(locateVehicleOnManufacturer(s2));
                                        break;
                                    }
                                }
                            }
                        });
                        System.out.println("Following parking slots meet your requirement: ");
                        listOfSlots.forEach(new Consumer<ParkingSlot>() {
                            @Override
                            public void accept(ParkingSlot parkingSlot) {
                                System.out.println(parkingSlot.getId());
                            }
                        });
                    }catch(Exception e){
                        System.out.println("Error: " + e);
                    }
                    break;
                }
                case 5: {
                    this.stop();
                    System.exit(0);
                }
                default: System.out.println("Please enter a value between 1 and 5");
            }
        }
    }
}
