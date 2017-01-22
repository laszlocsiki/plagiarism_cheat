package plagiarism;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        JFrame frame=new JFrame("Plagiarism avoider");
        frame.setContentPane(new mainForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
}