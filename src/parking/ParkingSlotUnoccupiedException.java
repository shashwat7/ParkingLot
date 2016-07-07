package parking;

/**
 * Created by shashwat on 7/7/16.
 */
public class ParkingSlotUnoccupiedException extends Exception {
    ParkingSlotUnoccupiedException(String s){
        super(s);
    }
}
