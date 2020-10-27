import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainArea extends JPanel {
    CopyOnWriteArrayList<JLabel> messages = new CopyOnWriteArrayList<JLabel>();
    CopyOnWriteArrayList<String> messages1 = new CopyOnWriteArrayList<String>();

    public MainArea() {
        // JLabel ll = new JLabel ("Test");
        // JLabel ll1 = new JLabel ("Test2");
        // this.add(ll);
        // this.add(ll1);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("IDIOT");
        // for (JLabel m : messages)
        int y = 30;
        for (String m : messages1) {
            g.drawString(m, 30, y);
            y += 30;
        }
    }

    public void printMessage(String mes) {
        messages1.add(mes);
        // JLabel mesa = new JLabel(mes);
        // messages.add(mesa);
        // this.add(mesa);
    }
}
