package parking;

import model.ParkingSlot;
import model.VehicleType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by shashwat on 7/6/16.
 */
public class Main {

    public static void main(String args[]) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String usage = "Usage: java parking.Main no_of_parking_slots";
        if(args.length != 1) {
            System.out.println(usage);
            System.exit(1);
        }


        /*
        *  Begin initializing the parking lot.
        * */
        boolean systemOn = true;
        int no_of_slots = Integer.parseInt(args[0].trim());
        PriorityQueue<ParkingSlot> slots= new PriorityQueue<>(no_of_slots, new ParkingSlotComparator());
        int occupancy = 0;
        // Initializing each parking slot with all possibilities of accomodation
        VehicleType[] canAccomodate = {VehicleType.TRUCK, VehicleType.CAR, VehicleType.BUS};
        for(int i = 0; i < no_of_slots; i++){
            slots.add(new ParkingSlot(i,canAccomodate));
        }
        // Creating a map of registrationNumber -> parkingSlot to easily locate the vehicle in the parking slot.
        HashMap<String,Integer> location = new HashMap<>();


        /*
        * Initialization done. Handling user input/output.
        * */
        while(systemOn){
            printMenu();
            switch (getUserChoice(br)){
                case 1 : {
                    System.out.println("1");
                    break;
                }
                case 2: {
                    System.out.println("2");
                    break;
                }
                case 3: {
                    System.out.println("2");
                    break;
                }
                case 4: {
                    System.out.println("2");
                    break;
                }
                case 5: {
                    systemOn = false;
                    System.exit(0);
                }
                default: System.out.println("Please enter a value between 1 and 5");
            }
        }
    }

    private static void printMenu(){
        String menu =
                "----------------------------------------------\n" +
                        "Enter one of the following options: \n" +
                        "1. Park a new car\n" +
                        "2. Unpark an existing parked car\n" +
                        "3. Print the status of the parking lot\n" +
                        "4. Search\n"+
                        "5. Exit\n"+
                "----------------------------------------------\n";
        System.out.println(menu);
    }

    private static int getUserChoice(BufferedReader br){
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

}