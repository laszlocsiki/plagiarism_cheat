package plagiarism;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Created by lcssgml on 1/12/17.
 */
public class DocumentHandle {

    public String inputFilePath;
    public String outputFilePath;
    public XWPFDocument doc;

    //constructor
    public DocumentHandle(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        try {
            this.doc=new XWPFDocument(OPCPackage.open(inputFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    //change word
    public String changeWord(String inputWord){
        String outputWord = null;
        //get first letter
        String firstLetter=getFirstLetters(inputWord);
        //get file which contains synonyms
        String filePath=get_sin_file(firstLetter);

        File file = new File(filePath);
        try {
            Scanner scanner = new Scanner(file);
            //read the file line by line
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String lineFromFile = scanner.nextLine();
                if (lineFromFile.contains(inputWord)) {
                    //a match
                    String line = lineFromFile;
                    outputWord = line.substring(line.indexOf(": ") + 2);
                    //System.out.println(synonim);
                } else {
                    String line = lineFromFile;
                    outputWord = "test";
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return outputWord;
    }

    //get the file which contains the synonyms
    public String get_sin_file(String letter){
        String letterFilePath = null;
        File dir = new File("Java/Plagiarism/synonyms/");
        FileFilter fileFilter = new WildcardFileFilter("*_"+letter+".txt");
        File[] files = dir.listFiles(fileFilter);
        for (int i = 0; i < files.length; i++) {
            letterFilePath=files[i].getAbsolutePath();
            System.out.println(letterFilePath);
        }
        if(letterFilePath==null) {
            letterFilePath = "Java/Plagiarism/synonyms/litera_a.txt";
            System.out.println("default file path -> file not found");
        }

        return letterFilePath;
    }

    //get the first letter
    public String getFirstLetters(String word) {
        String firstLetters = "";
        word = word.replaceAll("[.,]", ""); // Replace dots, etc (optional)
        for(String s : word.split(" "))
        {
            firstLetters += s.charAt(0);
        }
        return firstLetters;
    }

    //rebuild the document
    public void rebuildDocument(){
        try {
            this.doc.write(new FileOutputStream(this.outputFilePath));
            System.out.println("File saved to : "+this.outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void mainEngine(){
        for(XWPFParagraph p : this.doc.getParagraphs()){
            List<XWPFRun> runs = p.getRuns();
            if (runs != null){
                for (XWPFRun r :runs){
                    String text = r.getText(0);
                    if(text != null) {
                        //find the fifth words
                        String[] split = text.split(" ");
                        for (int i = 4; i < split.length; i = i + 5) {
                            System.out.print("original world: " + split[i]);
                            String currentWord = split[i];
                            System.out.println(" changed to: " + changeWord(currentWord));

                            text = text.replace(currentWord, changeWord(currentWord));
                        }
                        //rebuild text
                        r.setText(text, 0);
                    }
                }
            }
        }
        System.out.println("Main engine done");
        //rebuild doc (save)
        rebuildDocument();
    }
}
