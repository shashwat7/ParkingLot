package parking;

import model.ParkingSlot;
import model.ParkingSlotStatus;

import java.util.Comparator;

/**
 * Created by shashwat on 7/7/16.
 */
public class ParkingSlotComparator implements Comparator<ParkingSlot> {
    @Override
    public int compare(ParkingSlot p1, ParkingSlot p2){
        if(p1.getpStatus() == ParkingSlotStatus.FREE && p2.getpStatus() != ParkingSlotStatus.FREE)
            // p1 is free and p2 is occupied
            return -1;
        else if(p1.getpStatus() != ParkingSlotStatus.FREE && p2.getpStatus() == ParkingSlotStatus.FREE)
            // p1 is occupied and p2 is free
            return 1;
        else
            // both p1 and p2 are free or occupied. So, lesser the id more the priority.
            return (p1.getId()-p2.getId());

    }
}
