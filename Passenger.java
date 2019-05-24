/**
 * Hassan Raja 
 */
public class Passenger {
    /**
     * The Strings represent the passenger's name, departing location, arriving location
     * The time represent the time the passenger arrived at the airport
     * The int flightClass represents the priority of the class with First class as 1 and Economy as 4
     */
    private String name;
    private String departingLocation;
    private String arrivingLocation;
    private int timeArrived;
    private int flightClass;

    /**
     * This constructor creates a passenger object
     * @param newName inputs the passenger's name
     * @param destination inputs the arriving location
     * @param time inputs the time arrived at airport
     * @param newClass inputs the flight class of the passenger
     */
    public Passenger(String newName, String destination, int time, String newClass){
        this.name = newName;
        this.departingLocation = "ARK Airport NYC";
        this.arrivingLocation = destination;
        this.timeArrived = time;
        if(newClass.equals("First")){
            this.flightClass = 1;
        } else if(newClass.equals("Business")){
            this.flightClass = 2;
        } else if(newClass.equals("Premium Economy")){
            this.flightClass = 3;
        } else{
            this.flightClass = 4;
        }
    }

    /**
     * The to String method
     * @return string saying passenger's flight info
     */
    public String toString(){
        if(flightClass == 1){
            return "[" + name + ", First, " + timeArrived + " minutes] ";
        } else if(flightClass == 2){
            return "[" + name + ", Business, " + timeArrived + " minutes] ";
        } else if(flightClass == 3){
            return "[" + name + ", Premium Economy, " + timeArrived + " minutes] ";
        } else{
            return "[" + name + ", Economy, " + timeArrived + " minutes] ";
        }

    }

    /**
     * Getter method for name
     * @return passenger's name
     */
    public String getName(){
        return name;
    }

    /**
     * Mutator method for name
     * @param newName sets passenger's name
     */
    public void setName(String newName){
        name = newName;
    }

    /**
     * Getter method for departing location
     * @return the departing location
     */
    public String getDepartingLocation() {
        return departingLocation;
    }

    /**
     * Mutator method to set departing location
     * @param newLoc sets new departing location
     */
    public void setDepartingLocation(String newLoc){
        departingLocation = newLoc;
    }

    /**
     * Getter for arriving location
     * @return the arriving location
     */
    public String getArrivingLocation(){
        return this.arrivingLocation;
    }

    /**
     * Mutator to set arriving location
     * @param newLoc sets new arriving location
     */
    public void setArrivingLocation(String newLoc){
        arrivingLocation = newLoc;
    }

    /**
     * Getter for time arrived at airport
     * @return time arrived in double
     */
    public int getTimeArrived() {
        return timeArrived;
    }

    /**
     * Mutator for time arrived
     * @param timeArrived sets new time arrived
     */
    public void setTimeArrived(int timeArrived) {
        this.timeArrived = timeArrived;
    }

    /**
     * Getter for flight class
     * @return the passenger's class
     */
    public int getFlightClass() {
        return flightClass;
    }

    /**
     * Mutator for flight class
     * @param newClass sets new class for passenger
     */
    public void setFlightClass(String newClass) {
        if(newClass.equals("First")){
            this.flightClass = 1;
        } else if(newClass.equals("Business")){
            this.flightClass = 2;
        } else if(newClass.equals("Premium Economy")){
            this.flightClass = 3;
        } else{
            this.flightClass = 4;
        }
    }
}
