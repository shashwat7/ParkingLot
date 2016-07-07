# ParkingLot

It is a multi-threaded implementation of a parking lot, supporting multiple operators.
The parking lot has the following functions:
- Park a vehicle
- Unpark a vehicle
- Status of the parking lot
- Search in the parking lot.

Each operator of the parking slot, must be invoked as a seperate thread. Semaphore is used to attain a lock on the parking slot inventory.

# Running instructions:
1. Clone the repo
2. cd ParkingLot
3. Compile src files: javac src/exception/*.java src/model/*.java src/parking/*.java
4. Run the main function: 
	cd src/
	java -cp . parking.Main [numberOfParkingSlots] [NameOfOperator]
5. Follow the menu