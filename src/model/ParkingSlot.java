package model;

/**
 * Created by shashwat on 7/6/16.
 */

public class ParkingSlot {

    private int id;
    private VehicleType[] canAccomodate;
    private ParkingSlotStatus pStatus;
    private Vehicle occupiedBy;

    public ParkingSlot(int id, VehicleType[] canAccomodate){
        this.id = id;
        this.canAccomodate = canAccomodate;
        this.pStatus = ParkingSlotStatus.FREE;
        this.occupiedBy = null;
    }

    // Creating getters
    public int getId() {
        return id;
    }

    public VehicleType[] getCanAccomodate() {
        return canAccomodate;
    }

    public ParkingSlotStatus getpStatus() {
        return pStatus;
    }

    public Vehicle getOccupiedBy() {
        return occupiedBy;
    }

    // Creating setters
    // Note: id and canAccomodate cannot be changed after initialization.

    public void setpStatus(ParkingSlotStatus pStatus) {
        this.pStatus = pStatus;
    }

    public void setOccupiedBy(Vehicle v) {
        this.occupiedBy = v;
    }
}
