package QueuingAnalyse;

public class Client implements Comparable<Client>{
    private int id;
    private int arrivalTime;
    private int serviceTime;
    private int finishTime;

    @Override
    public String toString() {
        return "(" + id  +","+ arrivalTime +","+ serviceTime + ")";
    }

    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        finishTime = 0;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void decrementServiceTime(){
        serviceTime--;
    }

    public void incrementFinishTime(){
        finishTime++;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public int getId() {
        return id;
    }



    @Override
    public int compareTo(Client o) {
        return  arrivalTime-o.arrivalTime;
    }
}