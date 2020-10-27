import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    String name;
    BotPanel botPanel = new BotPanel();
    TopMenu topMenu = new TopMenu();
    MainArea mainArea = new MainArea();

    public MyFrame() {
        name = JOptionPane.showInputDialog(this, "Enter your name");
        if (name.isEmpty()) {
            this.closeIt();
        } else {
            getContentPane().setBackground(Color.DARK_GRAY);
            setTitle("Chat");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            // setResizable(false);
            setSize(1000, 1000);
            getContentPane().add(BorderLayout.SOUTH, botPanel);
            getContentPane().add(BorderLayout.NORTH, topMenu);
            getContentPane().add(BorderLayout.CENTER, mainArea);
        }
    }

    private void closeIt() {
        this.getContentPane().setVisible(false);
        this.dispose();
    }

    public String getName() {
        return name;
    }

    public BotPanel getBotPanel() {
        return botPanel;
    }

    public MainArea getMainArea() {
        return mainArea;
    }
}
