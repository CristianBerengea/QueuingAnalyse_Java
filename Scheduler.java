package QueuingAnalyse;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int  numberOfServers;
    private Strategy strategy;
    private Ui userI;

    public Scheduler(int  numberOfServers, Ui userI)
    {
        //create server object
        //create thread with object
        this.userI=userI;
        this.numberOfServers=numberOfServers;
        servers = new ArrayList<>();
        strategy = new TimeStrategy();
        for(int i=1;i<=numberOfServers;i++)
        {
            Server s = new Server();
            servers.add(s);
        }
    }

    public void choseStrategy(SimulationManager.ChosenStrategy x)
    {
        if(x == SimulationManager.ChosenStrategy.SHORTEST_QUEUE ) {
            strategy = new NumberStrategy();
        }else{
            strategy = new TimeStrategy();
        }
    }

    public void dispatchClient(Client t)
    {
        strategy.addClient(servers,t);
    }


    public List<Server> getServers() {
        return servers;
    }

    public boolean verifyQuequeHasClients(){
        for (Server s: servers) {
            if(s.getWaitingPeriod().intValue()>0) return true;
        }
        return false;
    }

    public String toString()
    {
        String r = "";
        for (int i=0;i<servers.size();i++) {
            String aux = servers.get(i).toString();
            if(aux=="") {
                aux += " closed";
                userI.setRed(i);
            }
            else userI.setGreen(i);

            r += "Queue "+(i+1)+":" +aux + '\n';
            userI.settVect(i,aux);
        }
        return r;
    }
}

