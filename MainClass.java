package QueuingAnalyse;

public class MainClass {
    public static void main(String[] args) {
        if(args.length==2){
            SimulationManager simulationManager = new SimulationManager(args[0],args[1]);
        Thread t = new Thread(simulationManager);
        t.start();
        }
        else System.out.println("invalid call");
    }
}
