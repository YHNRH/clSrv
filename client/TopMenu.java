import javax.swing.*;
import java.awt.*;

public class TopMenu extends JMenuBar
{
    JMenu m1 = new JMenu("FILE");
    JMenu m2 = new JMenu("Help");
    JMenuItem m11 = new JMenuItem("Open");
    JMenuItem m22 = new JMenuItem("Save as");
   
    
    public void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	m1.add(m11);
	m1.add(m22);
	this.add(m1);
	this.add(m2);
    }
}
