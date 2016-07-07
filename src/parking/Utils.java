package parking;

import model.ParkingSlot;
import model.ParkingSlotStatus;
import model.Vehicle;

/**
 * Created by shashwat on 7/7/16.
 */
public class Utils {

    // This method will park the vehicle at the parking slot.
    // If the parking slot is not free then it throws the error.
    public static void parkVehicle(Vehicle v, ParkingSlot p) throws ParkingSlotAlreadyOccupiedException{
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
    public static void unparkVehicle(Vehicle v, ParkingSlot p) throws ParkingSlotUnoccupiedException, ParkingSlotOccupiedByDifferentVehicle{
        if(p.getpStatus() != ParkingSlotStatus.OCCUPIED){
            throw new ParkingSlotUnoccupiedException("Parking Slot: " + p.getId() + " is already unoccupied");
        } else if(!p.getOccupiedBy().equals(v)){
            throw new ParkingSlotOccupiedByDifferentVehicle("Parking Slot: " + p.getId() + " is occupied by a different vehicle: " + p.getOccupiedBy().getRegistrationNumber());
        } else{
            p.setpStatus(ParkingSlotStatus.FREE);
            p.setOccupiedBy(null);
        }
    }


}
