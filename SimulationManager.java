package QueuingAnalyse;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class SimulationManager implements Runnable{
    private  int numberOfClients;
    private int numberOfServers;
    public int timeLimit;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int minProcessingTime;
    private int maxProcessingTime;
    // queue management and client distribution
    private Scheduler scheduler;
    //Clients
    private List<Client> generatedClients;

    private Ui userI;

    public enum ChosenStrategy{
        SHORTEST_QUEUE,SHORTEST_WAITING_TIME;
    };

    private String inPath;
    private String outPath;


    private boolean start = false;

    public SimulationManager(String inPath,String outPath)
    {
        this.inPath=inPath;
        this.outPath=outPath;
        if(readData()){
            //initialize the scheduler
            //create and start numbeOfServers servers threads
            generatedClients = new ArrayList<Client>();
            generateNRandomClients();
            userI = new Ui(numberOfServers,numberOfClients,timeLimit,minArrivalTime,maxArrivalTime,minProcessingTime,maxProcessingTime);
            scheduler = new Scheduler(numberOfServers,userI);
            userI.addListenerSt(new StartSt());
            userI.addListenerSn(new StartSn());
        }
    }

    class StartSt implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            scheduler.choseStrategy(SimulationManager.ChosenStrategy.SHORTEST_WAITING_TIME);
            userI.setSt();
            start = true;
        }
    }

    class StartSn implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            scheduler.choseStrategy(SimulationManager.ChosenStrategy.SHORTEST_QUEUE);
            userI.setSn();
            start = true;
        }
    }


    private boolean readData()
    {
        try{
            File file = new File(inPath);
            Scanner sc = new Scanner(file);
            boolean ok = true;
            if (sc.hasNextInt()) numberOfClients = sc.nextInt();
            else ok = false;
            if (sc.hasNextInt()) numberOfServers = sc.nextInt();
            else ok = false;
            if (sc.hasNextInt()) timeLimit = sc.nextInt();
            else ok = false;
            String buff="";
            if (sc.hasNextLine()) buff = sc.nextLine();
            else ok = false;
            if (sc.hasNextLine()) buff = sc.nextLine();
            else ok = false;
            String[] s= buff.split(",");
            minArrivalTime = Integer.parseInt(s[0]);
            maxArrivalTime = Integer.parseInt(s[1]);
            if (sc.hasNextLine()) buff = sc.nextLine();
            else ok = false;
            s = buff.split(",");
            minProcessingTime = Integer.parseInt(s[0]);
            maxProcessingTime = Integer.parseInt(s[1]);

            //System.out.println(numberOfClients+" "+numberOfServers+" "+timeLimit+" "+minArrivalTime+" "+maxArrivalTime+" "+minProcessingTime+" "+maxProcessingTime);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Could not open the file!");
            return false;
        }
        catch (Exception e)
        {
            System.out.println("Invalid file!");
            return false;
        }
        return true;
    }

    private void generateNRandomClients()
    {
        for(int i=1;i<=numberOfClients;i++){
            Random r = new Random();
            generatedClients.add(new Client(i,r.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime,r.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime));
        }
        Collections.sort(generatedClients);
        //System.out.println(generatedClients);
    }

    @Override
    public void run() {
        while (!start)
        {
            try
            {
                Thread.sleep(50);
            }
            catch(Exception e)
            {
                System.out.println("ERR");
            }
        }
        FileWriter myWriter=null;
        try {
             myWriter = new FileWriter(outPath);
        } catch (IOException e) {
            System.out.println("Could not open the output file");
        }
        int curentTime=0;
        int i=0;
        int j;
        while(curentTime<=timeLimit)
        {
            userI.setbTime(curentTime);
            while (i<numberOfClients&&generatedClients.get(i).getArrivalTime()<=curentTime) {
                {
                    scheduler.dispatchClient(generatedClients.get(i));
                    i++;
                }
            }
            j=i;
            String aux="";
            while(j<numberOfClients)
            {
                aux+=generatedClients.get(j);
                j++;
            }
            userI.setWaitingClients(aux);
            userI.setbTimeOrange();
            try
            {
                Thread.sleep(100);
            }
            catch(Exception e)
            {
                System.out.println("ERR");
            }
            userI.setbTimeBlue();
            String rez = scheduler.toString();
            //System.out.println("Time "+curentTime+"\n"+"Waiting clients: "+aux+"\n"+rez);
            userI.updateOutputText("Time "+curentTime+"\n"+"Waiting clients: "+aux+"\n"+rez);
            try {
                myWriter.write("Time "+curentTime+"\n"+"Waiting clients: "+aux+"\n"+rez);
            } catch (IOException e) {
                System.out.println("Could not open the output file");
            }

            if(i==j){
                // all clients are in queques
                if(!scheduler.verifyQuequeHasClients()) break;
            }

            try
            {
                Thread.sleep(900);
            }
            catch(Exception e)
            {
                System.out.println("ERR");
            }
            curentTime++;
        }
        i=0;
        List<Server> s = scheduler.getServers();
        for(Server e: s)
        {
            userI.setRed(i);
            i++;
            if(e.getT()!=null) e.getT().stop(); //daca sunt multe cozi, exista posibilitatea ca acestea sa nu se fi deschis vreodata
        }
        float sum=0;
        i=0;
        for (Client c: generatedClients) {
            if(c.getServiceTime()==0) {
                i++;
                sum+=c.getFinishTime();
            }
        }
        userI.setResultLabel("Average waiting time: "+sum/i+" s    Number of clients served: "+i+"/"+numberOfClients);
        userI.updateOutputText("\nAverage waiting time: "+sum/i+" s    Number of clients served: "+i+"/"+numberOfClients);
        //System.out.println(i+"  "+sum);
        try {
            myWriter.write("\nAverage waiting time: "+sum/i+" s   Number of clients served: "+i+"/"+numberOfClients);
        } catch (IOException e) {
            System.out.println("Could not open the output file");
        }
        try {
            myWriter.close();
        } catch (IOException e) {
            System.out.println("ERR");
        }
    }

}

