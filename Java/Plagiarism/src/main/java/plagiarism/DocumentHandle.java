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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lcssgml on 1/12/17.
 */
public class DocumentHandle {

    private String inputFilePath;
    private String outputFilePath;
    private XWPFDocument doc;

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

    //smallInput function
    private String smallInput(String fullWord){
        String smallInput=null;
        if(fullWord.length()<4){
            smallInput=fullWord;
        }else if((fullWord.length()>3) && (fullWord.length()<6)){
            smallInput=fullWord.substring(0,fullWord.length()-1);
        }else if((fullWord.length()>5) && (fullWord.length()<8)){
            smallInput=fullWord.substring(0,fullWord.length()-2);
        }else if(fullWord.length()>=8){
            smallInput=fullWord.substring(0,fullWord.length()-2);
        }
        //System.out.println(smallInput);
        return smallInput;
    }

    //this function check the word, if is word then true, else false;
    private boolean isWord(String inputWord){
        Pattern pattern=Pattern.compile("[^a-z0-9]",Pattern.CASE_INSENSITIVE);
        Matcher m= pattern.matcher(inputWord);
        boolean ret = m.find();
        //System.out.println(ret);
        if(!ret)
            return true;
        else
            return false;
    }

    //change word
    private String changeWord(String unformattedInput){
        String inputWord=unformattedInput.replaceAll("[.,”]", "");
        String outputWord = null;
        //get first letter
        String firstLetter=getFirstLetters(inputWord);
        //get file which contains synonyms
        if(firstLetter != null) {
            String filePath = get_sin_file(firstLetter);
            if(filePath==null){
                outputWord = "[Change this -> "+unformattedInput+"]";
                return outputWord;
            }

            File file = new File(filePath);

            if (inputWord.length() < 4){
                return inputWord;
            }else{
                try {
                    Scanner scanner = new Scanner(file);
                    //read the file line by line
                    while (scanner.hasNextLine()) {
                        String lineFromFile = scanner.nextLine();
                        String[] splitLine = lineFromFile.split(" ");
                        if (splitLine[0].contains(smallInput(inputWord))) {
                            //a match
                            String line = lineFromFile;
                            outputWord = "["+inputWord+" -> "+line.substring(line.indexOf(": ") + 2)+"]";
                            //System.out.println(synonim);
                            return outputWord;
                        }
                    }
                    if (outputWord == null) {
                        outputWord = "[Change this -> "+unformattedInput+"]";
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }else{
            outputWord = "[Change this -> "+inputWord+"]";
        }
        return outputWord;
        //TODO:this method remove the special characters, resolve this bug!
    }

    //get the file which contains the synonyms
    private String get_sin_file(String letter){
        String letterFilePath = null;
        File dir = new File("Java/Plagiarism/synonyms/");
        FileFilter fileFilter = new WildcardFileFilter("*_"+letter+".txt");
        File[] files = dir.listFiles(fileFilter);
        for (int i = 0; i < files.length; i++) {
            letterFilePath=files[i].getAbsolutePath();
           // System.out.println(letterFilePath);
        }
        if(letterFilePath==null) {
           // letterFilePath = "Java/Plagiarism/synonyms/litera_a.txt";
            //System.out.println("default file path -> file not found");
            return null;
        }

        return letterFilePath;
    }

    //get the first letter
    private String getFirstLetters(String word) {
        String firstLetters = "";
        word = word.replaceAll("[.,-]", ""); // Replace dots, etc (optional)
        if(word.equals("")){
            return null;
        }else{
            for(String s : word.split(" "))
            {
                firstLetters += s.charAt(0);
            }
        }
        return firstLetters;
    }

    //rebuild the document
    private void rebuildDocument(){
        try {
            this.doc.write(new FileOutputStream(this.outputFilePath));
            System.out.println("File saved to : "+this.outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public StringBuilder mainEngine(){
        StringBuilder returnString = new StringBuilder();
        int counter=1;

        for(XWPFParagraph p : this.doc.getParagraphs()){
            List<XWPFRun> runs = p.getRuns();
            if (runs != null){
                for (XWPFRun r :runs){
                    //r.setColor(String.valueOf(IndexedColors.RED.getIndex()));
                    String text = r.getText(0);
                    if(text != null) {
                        //find the fifth words
                        String[] split = text.split(" ");
                        for (int i = 4; i < split.length; i = i + 5) {
                            System.out.print("original world: " + split[i]);
                            String currentWord = split[i];
                            returnString.append(counter + ". Original world: "+currentWord);
                            returnString.append(" changed to: " + changeWord(currentWord)+"\n");
                            text = text.replace(currentWord, changeWord(currentWord));
                            //TODO: create a function witch return a RUN which contain the formatted text
                            counter++;
                        }
                        //rebuild text
                        //System.out.println("COLOR:"+r.getColor());
                        r.setText(text, 0);
                    }
                }
            }
        }
        System.out.println("Main engine done");
        //rebuild doc (save)
        rebuildDocument();
        return returnString;
    }
}

//TODO: find text between two quotes and skip that...