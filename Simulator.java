import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;
public class Simulator {
    private int numFlights;
    private int numArrived; //number of planes that landed
    private double arrivalProbability;

    public Simulator(int numFlights, int numArrived, double arrivalProbability) {
        this.numFlights = numFlights;
        this.numArrived = numArrived;
        this.arrivalProbability = arrivalProbability;
    }

    public void simulate(int duration) {
        if (duration < 0 || this.numFlights < 0 || this.arrivalProbability < 0.0 || this.arrivalProbability > 1.0 ||
        this.numFlights>25) {
            //if the parameters are negative, no simulation
            System.out.println("No simulation! Impossible parameters have been entered.");
            return;
        }
        String[] locations = new String[25]; //holds locations
        int numberLocation = 7; //the number of previous locations in array
        String[] names = new String[100]; //holds names
        String[] flightClass = {"First", "Business", "Premium Economy", "Economy"}; //holds the flight class
        File f = new File("names.txt"); // file contains 100 names
        File f2 = new File("locations.txt"); //file contains locations
        int j = 0;
        int k = 0;
        try {
            Scanner reader = new Scanner(f); //import 100 names into the names array
            Scanner locationReader = new Scanner(f2); //import locations into the locations array
            while (reader.hasNextLine()) {
                names[j] = reader.nextLine();
                j++;
            }
            while (locationReader.hasNextLine()) {
                locations[k] = locationReader.nextLine();
                k++;
            }
            reader.close();
            locationReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("error");
        }
        Scanner input = new Scanner(System.in);
        Airplane[] airplanes = new Airplane[25]; //assuming airport can hold 25 airplanes
        String[] userDestinations = new String[25]; //the user can set the destinations to the flights
        int numberUserEntered = 0;
        for(int i=0; i<numFlights; i++){ //create number of airplanes user wants
            airplanes[i] = new Airplane();
            System.out.print("Enter the destination of flight " + (i+1) + ": ");
            String userDestination = input.nextLine();
            numberLocation++;
            locations[numberLocation-1] = userDestination;
            userDestinations[i] = userDestination;
            numberUserEntered++;
            airplanes[i].setDestinationCity(userDestination);
        }
        boolean stillHere = false;
        PassengerQueue airportLine = new PassengerQueue(); //line at the airport
        int currentMin; //keeps track when people entered the queue
        for (currentMin = 1; currentMin <= duration; currentMin++) {
            System.out.println("Minute " + currentMin + ":");
            System.out.println("There are " + numFlights + " airplanes at the airport");
            for(int i = 0; i < numFlights; i++){
                System.out.println("Flight " + (i+1) + " is to " + airplanes[i].getDestinationCity() +
                        " with " + airplanes[i].airplaneSize() + " passengers");
            }
            for(int i=0; i<numFlights; i++){
                System.out.print((i+1+ "-"));
                airplanes[i].printLine();
                System.out.println();
            }

            //Event 1: did person arrive in the airport?
            if (Math.random() < arrivalProbability) { //if a passenger arrives, enqueue them
                airportLine.enqueue(new Passenger(names[(int)(Math.random()*names.length)],userDestinations[(int)(Math.random()*numberUserEntered)],
                        currentMin, flightClass[(int)(Math.random()*4)]));

                try{
                    //if the person matches a location waiting for them, enqueue them onto the airplane line
                    if(currentMin>30 && currentMin%5==0 && !stillHere){
                        System.out.println("Would you like to set the destination for the flight that arrived earlier?");
                        String s;
                        while(true){ //gives another chance if wrong input is given
                            s = input.nextLine();
                            if(s.equals("Y") || s.equals("N")){
                                break;
                            }
                            System.out.println("Please enter a Y (yes) or N (no)");
                        }

                        switch(s){ //used for the 2 cases
                            case "Y":
                                stillHere = true;
                                System.out.print("Where would you like it to go?: ");
                                String newLocation = input.nextLine();
                                numberLocation++;
                                locations[numberLocation-1] = newLocation;
                                numberUserEntered++;
                                userDestinations[numberUserEntered-1] = newLocation;
                                numFlights++; //increase the number of flights at the airport
                                airplanes[numFlights-1] = new Airplane(); //create new airplane within the airport
                                airplanes[numFlights-1].setDestinationCity(newLocation);
                                break;
                            case "N": // if not just wait for a flight to arrive
                                stillHere = false;
                                System.out.println("Waiting for flight to land...");
                        }

                    }
                    for(int i = 0; i<numFlights; i++){
                        //if the person's flight matches with flight class and time to take off > 5, enqueue
                        boolean matching = airportLine.peek().getArrivingLocation().equals(airplanes[i].getDestinationCity());
                        if( matching &&
                                (airplanes[i].getTimeToTakeoff() - currentMin > 5) &&
                                airportLine.peek().getFlightClass() == 1){
                            airplanes[i].enqueueFirstClass(airportLine.dequeue());
                            break;
                        } else if(matching && (airplanes[i].getTimeToTakeoff() - currentMin > 5) && airportLine.peek().getFlightClass() == 2){
                            airplanes[i].enqueueBusinessClass(airportLine.dequeue());
                            break;
                        } else if(matching && (airplanes[i].getTimeToTakeoff() - currentMin >5) && airportLine.peek().getFlightClass() == 3){
                            airplanes[i].enqueuePremiumClass(airportLine.dequeue());
                            break;
                        } else if(matching && (airplanes[i].getTimeToTakeoff()-currentMin >5) && airportLine.peek().getFlightClass() == 4){
                            airplanes[i].enqueueEconomy(airportLine.dequeue());
                            break;
                        }
                    }
                } catch(EmptyQueueException ex){
                    System.out.println("oopss");
                }
            }
            for(int i =0; i <numFlights; i++){
                //Event 2: if(airplane.gettime() - currenttime == 0) use unload to print that the plane left with how many passengers
                if((airplanes[i].getTimeToTakeoff() - currentMin)%30 == 0){
                    System.out.println("The flight to " + airplanes[i].getDestinationCity() + " is taking off with " +
                            airplanes[i].unload() + " passengers");
                    airplanes[i] = airplanes[numFlights-1]; //switch with last plane in list then delete it
                    airplanes[numFlights-1] = null;
                    numFlights--; //decrement numFlights
                    numberUserEntered--;
                    i = -1;
                }
            }
            //Event 3: if currentTime%20==0 create a new plane and ask user to set the location and continue simulation
            if(currentMin%20 == 0){
                System.out.println("A new flight has arrived from " + locations[(int)(Math.random()*numberLocation-1)] +
                        ". It will be refueling for now!");
                numArrived++;
            }
            if(currentMin%30 == 0){ //after 10 min create a new airplane and ask user if they want to set location
                System.out.print("The flight that returned is ready to depart.\nWould you like to set a " +
                        "location for it? (Y or N): ");
                String s;
                while(true){ //gives another chance if wrong input is given
                    s = input.nextLine();
                    if(s.equals("Y") || s.equals("N")){
                        break;
                    }
                    System.out.println("Please enter a Y (yes) or N (no)");
                }

                switch(s){ //used for the 2 cases
                    case "Y":
                        stillHere = true;
                        System.out.print("Where would you like it to go?: ");
                        String newLocation = input.nextLine();
                        numberLocation++;
                        locations[numberLocation-1] = newLocation;
                        numberUserEntered++;
                        userDestinations[numberUserEntered-1] = newLocation;
                        numFlights++; //increase the number of flights at the airport
                        airplanes[numFlights-1] = new Airplane(); //create new airplane within the airport
                        airplanes[numFlights-1].setDestinationCity(newLocation);
                        break;
                    case "N": // if not just wait for a flight to arrive
                        stillHere = false;
                        System.out.println("Waiting for flight to land...");
                }

            }
        }
        System.out.println("Thank you for working with us. If you need more updates, please let us know!");
        input.close();
    }

    public static void main(String[] a) {
        System.out.println("Welcome to ARK Private International Airport!\n");
        Scanner input = new Scanner(System.in);
        int numFlights; //flights waiting to take off
        double arrivalProbability;
        int duration;
        while(true){ //in a loop so if user inputs wrong info the program does not crash
            try{
                System.out.print("Enter the number of airplanes waiting to takeoff: ");
                numFlights = Integer.parseInt(input.nextLine());
                //figure out how to ask for locations
                System.out.print("Enter the arrival probability: ");
                arrivalProbability = Double.parseDouble(input.nextLine());
                System.out.print("Enter the duration of the simulation: ");
                duration = Integer.parseInt(input.nextLine());
                break;
            } catch(IllegalArgumentException ex){
                System.out.println("Enter information correctly please");
            } catch(InputMismatchException ex2){
                System.out.println("Please enter info correctly");
            }
        }
        Simulator simulation = new Simulator(numFlights, 0, arrivalProbability);
        simulation.simulate(duration);
        input.close();

    }
}