package plagiarism;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by lcssgml on 1/18/17.
 */
public class mainForm {
    private JTextArea Display;
    private JButton convertButton;
    private JTextField outputField;
    private JTextField inputField;
    private JPanel panelMain;
    private JButton openButton;
    private JScrollPane jscroll;
    DocumentHandle documentHandle;
    private String inputPath;
    private String outputPath;

    public mainForm() {
        ///Display=new JTextArea();
        //jscroll=new JScrollPane(Display);
        //panelMain.add(jscroll);
       // inputField.setText("/home/lcssgml/IdeaProjects/plagiarism_cheat/Java/Plagiarism/Docs/input1.docx");
       // outputField.setText("/home/lcssgml/IdeaProjects/plagiarism_cheat/Java/Plagiarism/Docs/output1.docx");

        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File("/home/lcssgml/IdeaProjects/plagiarism_cheat/Java/Plagiarism/Docs"));
                int result = fileChooser.showOpenDialog(panelMain);
                if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                inputPath=selectedFile.getAbsolutePath();
                outputPath=inputPath;
                inputField.setText(inputPath);
                outputField.setText(inputPath);

                }
            }
        });

        convertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                String inputPath=inputField.getText();
                String outputPath=outputField.getText();

                documentHandle=new DocumentHandle(inputPath,outputPath);
                StringBuilder content=documentHandle.mainEngine();
                Display.setText(String.valueOf(content));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame=new JFrame("Plagiarism avoider");
        frame.setContentPane(new mainForm().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setVisible(true);
    }
}
