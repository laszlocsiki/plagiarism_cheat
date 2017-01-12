package plagiarism;

import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;

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
    }
}