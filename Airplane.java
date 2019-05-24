public class Airplane {
    /**
     * firstClassLine is the first class queue for the plane and has a capacity of 5
     * businessClassLine is the business class queue for the plane and has a capacity of 5
     * premiumClassLine is the premium economy queue for the plane and has a capacity of 10
     * economyLine is the economy class queue for the plane and has a capacity of 15
     * departureCity is the city that the plane is leaving
     * destinationCity is the plane's destination
     * timeToTakeoff takes 30 min until the plane leaves the airport
     */
    private String departureCity = "ARK Airport NYC";
    private String destinationCity;
    private int timeToTakeoff = 30;
    final int CAPACITY = 35;
    final int FIRSTCLASSCAPACITY = 5;
    private PassengerQueue firstClassLine;
    final int BUSINESSCAPACITY = 5;
    private PassengerQueue businessClassLine;
    final int PREMIUMCAPACITY = 10;
    private PassengerQueue premiumClassLine;
    final int ECONOMYCAPACITY = 15;
    private PassengerQueue economyLine;

    /**
     * constructor creates a queue for the airline
     */
    public Airplane(){
        firstClassLine = new PassengerQueue();
        businessClassLine = new PassengerQueue();
        premiumClassLine = new PassengerQueue();
        economyLine = new PassengerQueue();
    }

    /**
     * enqueues first class passengers
     * @param firstClass Passenger object
     */
    public void enqueueFirstClass(Passenger firstClass){
        if(firstClassLine.size() < FIRSTCLASSCAPACITY){
            this.firstClassLine.enqueue(firstClass);
        } else{
            System.out.println("All first class seats taken");
        }
    }

    /**
     * enqueues business class
     * @param businessClass passenger object
     */
    public void enqueueBusinessClass(Passenger businessClass){
        if(businessClassLine.size() < BUSINESSCAPACITY){
            this.businessClassLine.enqueue(businessClass);
        } else{
            System.out.println("All business class seats taken");
        }
    }

    /**
     * enqueues premium economy
     * @param premiumClass passenger object
     */
    public void enqueuePremiumClass(Passenger premiumClass){
        if(premiumClassLine.size() < PREMIUMCAPACITY){
            this.premiumClassLine.enqueue(premiumClass);
        } else{
            System.out.println("All premium economy seats taken");
        }
    }

    /**
     * enqueues economy
     * @param economyClass passenger object
     */
    public void enqueueEconomy(Passenger economyClass){
        if(economyLine.size() < ECONOMYCAPACITY){
            this.economyLine.enqueue(economyClass);
        } else {
            System.out.println("All economy seats taken");
        }
    }

    /**
     * Checks is the plane is full
     * @return true if the plane is full
     */
    public boolean isFull(){
        return (firstClassLine.size() + businessClassLine.size() + premiumClassLine.size() +
                economyLine.size() == CAPACITY);
    }

    /**
     * Prints the passengers on the airplane in class order
     */
    public void printLine(){
        System.out.print(destinationCity + ": ");
        if((firstClassLine.isEmpty()) && (businessClassLine.isEmpty()) && (premiumClassLine.isEmpty()) &&
                (economyLine.isEmpty())){
            System.out.print("");
        } else{
            try{
                PassengerQueue temp = new PassengerQueue();
                while(!firstClassLine.isEmpty()){
                    System.out.print(firstClassLine.peek().toString());
                    temp.enqueue(firstClassLine.dequeue());
                }
                if(temp != null){
                    firstClassLine = temp; //since both queues are the same order we set original to temp
                }
                temp = new PassengerQueue(); //set this to null to use for other lines as well
                while(!businessClassLine.isEmpty()){
                    System.out.print(businessClassLine.peek().toString());
                    temp.enqueue(businessClassLine.dequeue());
                }
                if(temp != null){
                    businessClassLine = temp;
                }
                temp = new PassengerQueue();
                while(!premiumClassLine.isEmpty()){
                    System.out.print(premiumClassLine.peek().toString());
                    temp.enqueue(premiumClassLine.dequeue());
                }
                if(temp!=null){
                    premiumClassLine = temp;
                }
                temp = new PassengerQueue();
                while(!economyLine.isEmpty()){
                    System.out.print(economyLine.peek().toString());
                    temp.enqueue(economyLine.dequeue());
                }
                if(temp!=null){
                    economyLine = temp;
                }
            } catch(EmptyQueueException ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * tells us how many passengers the plane takes off with
     * @return the number of passengers flying
     */
    public int unload(){
        int counter = 0;
        try{
            while(!firstClassLine.isEmpty()){
                firstClassLine.dequeue();
                counter++;
            }
            while(!businessClassLine.isEmpty()){
                businessClassLine.dequeue();
                counter++;
            }
            while(!premiumClassLine.isEmpty()){
                premiumClassLine.dequeue();
                counter++;
            }
            while(!economyLine.isEmpty()){
                economyLine.dequeue();
                counter++;
            }
        } catch(EmptyQueueException ex){
            System.out.println(ex.getMessage() + "   l");
        }
        return counter;
    }

    /**
     * @return how many people are on the plane
     */
    public int airplaneSize(){
        int people = 0;
        people = (firstClassLine.size()+businessClassLine.size()+premiumClassLine.size()+economyLine.size());
        return people;
    }

    /**
     * @return the time the plane has to take off
     */
    public int getTimeToTakeoff() {
        return timeToTakeoff;
    }

    /**
     * @return the departure city NYC
     */
    public String getDepartureCity() {
        return departureCity;
    }

    /**
     * @return the destination of the plane
     */
    public String getDestinationCity() {
        return destinationCity;
    }

    /**
     * @param destinationCity is set to be the planes destination
     */
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }
}
