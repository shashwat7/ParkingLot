package parking;

/**
 * Created by shashwat on 7/7/16.
 */
public class ParkingSlotAlreadyOccupiedException extends Exception {
    ParkingSlotAlreadyOccupiedException(String s){
        super(s);
    }
}
