package QueuingAnalyse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Queue;

public class Ui extends JFrame{
    private JButton bTime;
    private JButton[] bVect;
    private  JButton[] tVect;
    private JLabel waitingClients;
    private JButton bResult;
    private JTextArea txt;
    private JButton bStartSt;
    private JButton bStartSn;
    public Ui(int n,int numberOfClienti,int simulationTime,int minArrivalTime,int maxArrivalTime,int minServiceTime,int maxServiceTime)
    {
        bStartSt = new JButton("START Time Strategy");
        bStartSn = new JButton("START Number Of Clients Strategy");
        txt = new JTextArea("Output file:\n");
        bResult = new JButton("");
        bTime = new JButton("Time: 0");
        bTime.setEnabled(false);
        bVect = new JButton[n];
        tVect = new JButton[n];
        waitingClients = new JLabel("");
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        JScrollPane scp = new JScrollPane(content,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        JScrollPane scp2 = new JScrollPane(txt,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        JPanel p1 = new JPanel();
        p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
        JPanel p2 = new JPanel();
        p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
        p2.add(bStartSt);
        p2.add(bStartSn);
        p2.add(bTime);
        bResult.setEnabled(false);
        bResult.setVisible(false);
        bResult.setBackground(Color.yellow);
        p2.add(bResult);
        JLabel a = new JLabel("Number of clients: "+numberOfClienti+"    Simulation time: "+simulationTime+"     Minim Arrival Time: "+minArrivalTime+"      Maxim Arrival Time: "+maxArrivalTime+"    Minim Processing Time: "+minServiceTime+"    Maxim Service Time: "+maxServiceTime);
        p2.add(a);
        p2.add(waitingClients);
        for(int i=0;i<n;i++)
        {
            bVect[i] = new JButton("Queue "+(i+1)+":");
            bVect[i].setEnabled(false);
            bVect[i].setBackground(Color.red);
            p.add(bVect[i]);
            tVect[i] = new JButton("  ");
            tVect[i].setEnabled(false);
            p1.add(tVect[i]);
        }

        content.add(p2,BorderLayout.NORTH);
        content.add(p,BorderLayout.WEST);
        content.add(p1,BorderLayout.CENTER);
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scp, scp2);
        split.setDividerLocation(420);

        this.setContentPane(split);
        this.pack();
        setTitle("Queuing Analyse");
        setVisible(true);
        setSize(1370, 730);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void settVect(int i,String s) {
        tVect[i].setText(s);
    }
    public void setGreen(int i) {
        bVect[i].setBackground(Color.green);
    }
    public void setRed(int i) {
        bVect[i].setBackground(Color.red);
    }
    public void setbTime(int i) {
        bTime.setText("Time: "+i);
    }
    public void setbTimeOrange() {
        bTime.setBackground(Color.ORANGE);
    }
    public void setbTimeBlue() {
        bTime.setBackground(Color.BLUE);
    }
    public void setWaitingClients(String s){
        waitingClients.setText("Waiting Clients: "+s);
    }

    public void setResultLabel(String s){
        bResult.setText(s);
        bResult.setVisible(true);
    }

    public void updateOutputText(String s){
        txt.append(s);
    }

    public void addListenerSt(ActionListener ls) { bStartSt.addActionListener(ls); }
    public void addListenerSn(ActionListener ls) {
        bStartSn.addActionListener(ls);
    }
    public void setSt(){
        bStartSt.setText("Time Strategy");
        bStartSt.setEnabled(false);
        bStartSn.setVisible(false);
    }
    public void setSn(){
        bStartSn.setText("Number Of Clients Strategy");
        bStartSn.setEnabled(false);
        bStartSt.setVisible(false);
    }
}
