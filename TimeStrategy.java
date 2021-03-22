package QueuingAnalyse;

import java.util.List;

public class TimeStrategy implements  Strategy{


    @Override
    public void addClient(List<Server> servers, Client c) {
        int min = Integer.MAX_VALUE;
        Server s = null;
        for (Server e: servers ) {
            if(e.getWaitingPeriod().intValue()<min) {
                min = e.getWaitingPeriod().intValue();
                s=e;
            }
        }
            s.addClient(c);
    }
}

