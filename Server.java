package QueuingAnalyse;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;
    private AtomicInteger clientsNumber;
    private Client curentClient;
    private Thread t;
    public Server()
    {
        //initialize queue
        //increment the waiting time
        //t = new Thread(this::run);
        clients =  new ArrayBlockingQueue<Client>(1024);
        waitingPeriod = new AtomicInteger(0);
        clientsNumber = new AtomicInteger(0);
    }

    public void addClient(Client client){
        //add client to Clients queue
        //increase waitingPeriod
        try {
            clients.put(client);
            waitingPeriod.addAndGet(client.getServiceTime());
            clientsNumber.incrementAndGet();
        } catch (InterruptedException e) {
            System.out.println("Intrerupted thread!");
        }
        if(t!=null&&!t.isAlive()||t==null)
        {
            initT();
            t.start();
        }
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public Thread getT() {
        return t;
    }

    public void initT() {
        t = new Thread(this::run);
    }

    public int getClientsNumber() {
        return clientsNumber.get();
    }

    public String toString()
    {
        String s = "";
        if(curentClient!=null) s += " ("+curentClient.getId()+","+curentClient.getArrivalTime()+ "," +curentClient.getServiceTime() +");  ";
        for (Client c: clients) {
            s+=  " ("+c.getId()+","+c.getArrivalTime()+ "," +c.getServiceTime() +");";
        }
        return s;
    }

    @Override
    public void run() {
        //take next Client from queue
        //stop the thread with the Client serving time
        //decrement the waitingPeriod
        while(waitingPeriod.get()>=0) {
            if(curentClient == null && !clients.isEmpty())
            {
                try {
                    curentClient = clients.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else   if(curentClient != null)
            {
                if(curentClient.getServiceTime()>0)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("Intrerupted");
                    }
                    curentClient.decrementServiceTime();
                    waitingPeriod.decrementAndGet();
                    for (Client c: clients) {
                        c.incrementFinishTime();
                    }
                    curentClient.incrementFinishTime();
                }else{
                    curentClient=null;
                    clientsNumber.decrementAndGet();
                }
            }
        }
    }
}