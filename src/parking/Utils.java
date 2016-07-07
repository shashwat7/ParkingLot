package parking;

import exception.ParkingSlotAlreadyOccupiedException;
import exception.ParkingSlotOccupiedByDifferentVehicleException;
import exception.ParkingSlotUnoccupiedException;
import model.ParkingSlot;
import model.ParkingSlotStatus;
import model.Vehicle;
import model.VehicleType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shashwat on 7/7/16.
 */
public class Utils {

    // This method will park the vehicle at the parking slot.
    // If the parking slot is not free then it throws the error.
    public static void parkVehicle(Vehicle v, ParkingSlot p) throws ParkingSlotAlreadyOccupiedException {
        if(p.getpStatus() != ParkingSlotStatus.FREE)
        {
            throw new ParkingSlotAlreadyOccupiedException(
                    "Cannot park vehicle: " + v.getRegistrationNumber() +
                            " at parking slot number: " + p.getId()
            );
        }
        else{
            p.setpStatus(ParkingSlotStatus.OCCUPIED);
            p.setOccupiedBy(v);
        }
    }

    // This method will unpark a vehicle from the parking slot.
    // It will throw an error if the parking slot is unoccupied or occupied by a different vehicle.
    public static void unparkVehicle(Vehicle v, ParkingSlot p) throws ParkingSlotUnoccupiedException, ParkingSlotOccupiedByDifferentVehicleException {
        if(p.getpStatus() != ParkingSlotStatus.OCCUPIED){
            throw new ParkingSlotUnoccupiedException("Parking Slot: " + p.getId() + " is already unoccupied");
        } else if(!p.getOccupiedBy().equals(v)){
            throw new ParkingSlotOccupiedByDifferentVehicleException("Parking Slot: " + p.getId() + " is occupied by a different vehicle: " + p.getOccupiedBy().getRegistrationNumber());
        } else{
            p.setpStatus(ParkingSlotStatus.FREE);
            p.setOccupiedBy(null);
        }
    }

    // Print choices to the end-user
    public static void printMenu(){
        String menu = "----------------------------------------------\n" +
                        "Enter one of the following options: \n" +
                        "1. Park a new car\n" +
                        "2. Unpark an existing parked car\n" +
                        "3. Print the status of the parking lot\n" +
                        "4. Search\n"+
                        "5. Exit\n"+
                        "----------------------------------------------\n";
        System.out.println(menu);
    }

    // Take input from the end-user
    public static int getUserChoice(BufferedReader br){
        try{
            return Integer.parseInt(br.readLine().trim());
        } catch (IOException e) {
            System.err.println("Error: " + e);
            return -1;
        } catch (NumberFormatException e) {
            System.err.println("Invalid number");
            return -1;
        }
    }

    // Expects line as: [Type]|[Manufacturer]|[Model]|[RegistrationNumber]|[Color]
    public static Vehicle createVehicleObj(String line){
        //TO-DO: Add check to see if line is in correct format
        String[] arr= line.split("\\,");
        if(arr.length!=5) return null;
        VehicleType vType = null;
        switch(arr[0].toLowerCase()){
            case "car": {
                vType = VehicleType.CAR;
                break;
            }
            case "truck" : {
                vType = VehicleType.TRUCK;
                break;
            }
            case "bus" : {
                vType = VehicleType.BUS;
                break;
            }
            default: System.out.println("Vehicle Type be either of: car/truck/bus");
        }
        String model = arr[2];
        String manufacturer = arr[1];
        String color = arr[4];
        String regNum = arr[3];
        System.out.println("Type: " + vType+", "+
                "Manufacturer: "+manufacturer+", "+
                "Model: "+model+", "+
                "RegistrationNumber: "+regNum+", "+
                "Color: "+color);
        return new Vehicle(vType, model, manufacturer, regNum, color);
    }

    public static boolean isVehicleFitForSlot(Vehicle v, ParkingSlot p){
        VehicleType[] canFit = p.getCanAccomodate();
        for(int i=0;i<canFit.length;i++){
            if(canFit[i] == v.getvType()) return true;
        }
        return false;
    }

    public static Map<String, String> readSearchParameters(String line){
        String[] arr= line.split(" ");
        if(arr.length %2 == 0 || arr.length <3) {
            System.out.println("Invalid options");
            return null; // there can only be odd number of elements
        }
        Map<String, String> m = new HashMap<>();
        String flag = "", option = "";
        for(int i = 1; i<arr.length;i++){
            if(i%2==1){
                // Is a flag
                flag = arr[i].substring(1); // Everything except the '-'
                switch(flag){
                    case "t" : break;
                    case "r" : break;
                    case "m" : break;
                    case "cl" : break;
                    case "co" : break;
                    default : {
                        flag = "";
                        System.out.println(flag + " is not a valid flag.");
                    }
                }
            }
            else {
                // Is an option
                option = arr[i];
                if(flag!="") m.put(flag,option);
            }
        }
        return m;
    }

    public static <K,V> boolean appendToListInMap(HashMap<K,List<V>> multiMap, K k, V v){
        List<V> l;
        if(multiMap.containsKey(k)) l = multiMap.get(k);
        else l = new ArrayList<>();
        if(l.add(v)){
            multiMap.put(k,l);
            return true;
        } else return false;
    }
    public static <K,V> boolean removeFromListInMap(HashMap<K,List<V>> multiMap, K k, V v){
        List<V> l;
        if(multiMap.containsKey(k)) l = multiMap.get(k);
        else{
            // throw new NoSuchKeyExists();
            return false;
        }
        if(l.remove(v)){
            multiMap.put(k,l);
            return true;
        } else{
            return false;
        }
    }

}
