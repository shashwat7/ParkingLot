package parking;

/**
 * Created by shashwat on 7/7/16.
 */
public class ParkingSlotOccupiedByDifferentVehicle extends Exception {
    ParkingSlotOccupiedByDifferentVehicle(String s){
        super(s);
    }
}
