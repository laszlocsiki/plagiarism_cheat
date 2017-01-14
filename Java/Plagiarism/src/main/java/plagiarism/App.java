package plagiarism;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
         String inputFile="/home/lcssgml/IdeaProjects/plagiarism_cheat/Java/Plagiarism/Docs/input1.docx";
         String outputFile="/home/lcssgml/IdeaProjects/plagiarism_cheat/Java/Plagiarism/Docs/output1.docx";
         DocumentHandle documentHandle=new DocumentHandle(inputFile,outputFile);
         documentHandle.mainEngine();

         //Checked with http://www.plagscan.com/plagiarism-check/
         //and it's working! :D YEEE

        /*String letterFilePath = null;
        File dir = new File("Java/Plagiarism/synonyms/");
        FileFilter fileFilter = new WildcardFileFilter("*_t.txt");
        File[] files = dir.listFiles(fileFilter);
        for (int i = 0; i < files.length; i++) {
            letterFilePath=files[i].getAbsolutePath();
            System.out.println(letterFilePath);
        }
        if(letterFilePath==null) {
            letterFilePath = "Java/Plagiarism/synonyms/litera_a.txt";
            System.out.println(letterFilePath);
        }*/

        //This is the regex example
       /* System.out.println("******* Regex example (text between qutoes *********");
        String str= "\"This is my fucked string\" and this is not";
        final Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher m=pattern.matcher(str);
        while(m.find()){
            System.out.println(m.group(1));
        }*/
        // final String[] result = pattern.split(str);
        //System.out.println(Arrays.toString(result));

    }
}