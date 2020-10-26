import javax.swing.*;
import java.awt.*;

public class BotPanel extends JPanel
{
        JLabel label = new JLabel("Enter Text");
        JTextField tf = new JTextField(10); // accepts upto 10 characters
        JButton send = new JButton("Send");
   
    public void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	this.add(label);
	this.add(tf);
	this.add(send);

    }

    public JButton getButton()
    {
	return send;
    }

    public JTextField getTextField()
    {
	return tf;
    }
}
