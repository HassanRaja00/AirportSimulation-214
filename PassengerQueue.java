public class PassengerQueue {
    /**
     * Implementing queue using array with front and rear references
     * size tells us how many people are on the line
     */
    private Passenger[] passengers;
    private int front;
    private int rear;
    private int size;

    /**
     * Constructor for an empty queue
     */
    public PassengerQueue(){
        front = -1;
        rear = -1;
        size = 0;
        passengers = new Passenger[35];
    }

    /**
     * isEmpty method
     * @return true if the front is -1 showing the queue is empty
     */
    public boolean isEmpty(){
        return (front == -1)||(size==0);
    }

    /**
     * adds a new passenger to the end of queue
     * @param newPassenger is added to the Passenger[]
     */
    public void enqueue(Passenger newPassenger){
        front = 0;
        if(rear+1 == passengers.length){ //if adding 1 to rear equals front, create bigger line and copy elements over
            Passenger[] biggerLine = new Passenger[passengers.length*2+1];
            for(int i = 0; i<size; i++){
                biggerLine[i] = passengers[i];
            }
            passengers = biggerLine;
            rear = size-1;
        }

        rear = front == -1 ? 0 : rear + 1;
//        if(front == -1){ //is queue is empty
//            rear = 0;
//        }else{
//            rear = rear + 1;
//        }
        passengers[rear] = newPassenger;
        size++;
    }

    /**
     * @return the first element in array
     * @throws EmptyQueueException if it is empty and you try to dequeue
     */
    public Passenger dequeue() throws EmptyQueueException{ //NOTE: learn to remove by class
        if(front == -1){ //is queue is empty, throw exception
            throw new EmptyQueueException("Empty Queue!");
        }
        Passenger temp = passengers[front];
        if(front == rear){ //if there is only one element, make queue empty
            passengers[front] = null;
            passengers[rear] = null;
            front = -1;
            rear = -1;
        } else{ //shift everything up front
            passengers[front] = null;
            for(int i = front; i < rear; i++){
                passengers[i] = passengers[i+1];
            }
            rear = rear-1;
        }
        size--;
        return temp;
    }

    /**
     * @return the number of people on line
     */
    public int size(){
        return size;
    }

    /**
     * @return the first most element in the queue
     * @throws EmptyQueueException if queue is empty
     */
    public Passenger peek()throws EmptyQueueException{
        if(front == -1){
            throw new EmptyQueueException("Queue is empty!");
        }else{
            return passengers[front];
        }
    }

}

/**
 * class for creating empty queue exception
 */
class EmptyQueueException extends Exception{
    public EmptyQueueException(String message){
        super(message);
    }
}