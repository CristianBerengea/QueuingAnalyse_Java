package QueuingAnalyse;

import java.util.List;

public class NumberStrategy implements Strategy{

    @Override
    public void addClient(List<Server> servers, Client c) {
        int min = Integer.MAX_VALUE;
        Server s = null;
        for (Server e: servers ) {
            if(e.getClientsNumber()<min) {
                min = e.getClientsNumber();
                s=e;
            }
        }
        s.addClient(c);
    }
}
