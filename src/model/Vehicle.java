package model;

/**
 * Created by shashwat on 7/6/16.
 */
public class Vehicle {

    private VehicleType vType;
    private String model;
    private String manufacturer;
    private String registrationNumber;
    private String color;

    public Vehicle(VehicleType vType, String model, String manufacturer, String registrationNumber, String color){
        this.vType = vType;
        this.model = model;
        this.manufacturer = manufacturer;
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    // Creating setters
    // Note: Once the vehicle is initialized, it's model, manufacturer, registrationNumber and type cannot be changed.

    public void setColor(String color) {
        this.color = color;
    }

    // Creating getters

    public String getModel() {
        return model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getColor() {
        return color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public VehicleType getvType() {
        return vType;
    }

    public boolean equals(Vehicle v){
        return this.registrationNumber == v.getRegistrationNumber();
    }
}
