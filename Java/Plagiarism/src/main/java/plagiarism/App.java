package plagiarism;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        //This is the regex example
        System.out.println("******* Regex example (text between qutoes *********");
        String str = "\"This is my fucked string\" and this is not";
        final Pattern pattern = Pattern.compile("\"(.*?)\"");
        Matcher m = pattern.matcher(str);
        while (m.find()) {
            System.out.println(m.group(1));
        }
        // final String[] result = pattern.split(str);
        //System.out.println(Arrays.toString(result));

        //THIS IS DE DOCX READING
        System.out.println("******* .docx read *********");
        readDocxFile("/home/lcssgml/IdeaProjects/fifthelement/src/test1.docx");

    }

    private static void doYourJob(String paragraph) {
        //This is the change word example
        System.out.println("******* word change *********");
        //String a="asdsadsa dsads adasd otodiknem csuf ugruqiwbcasj yudjkasdnqwug ncmabkhduq otodiknem csuf asdsadsa dsads adasd otodiknem csuf ugruqiwbcasj yudjkasdnqwug ncmabkhduq otodiknem csuf asdsadsa dsads adasd otodiknem csuf ugruqiwbcasj yudjkasdnqwug ncmabkhduq otodiknem csuf";
        String[] split = paragraph.split(" ");
        for (int i = 4; i < split.length; i = i + 5) {
            System.out.print("Original: " + split[i]);
            String currentWord = split[i];
            System.out.println(" changed to: " + changeWord(currentWord));
            split[i] = changeWord(currentWord);
        }
        System.out.println(Arrays.toString(split).replace(",", " ").replaceAll("[\\[\\]]", ""));


    }

    private static String changeWord(String word) {
        String synonim = "";
        File file = new File("/home/lcssgml/IdeaProjects/fifthelement/src/dic.txt");

        try {
            Scanner scanner = new Scanner(file);
            //read the file line by line
            int lineNum = 0;
            while (scanner.hasNextLine()) {
                String lineFromFile = scanner.nextLine();
                if (lineFromFile.contains(word)) {
                    //a match
                    String line = lineFromFile;
                    synonim = line.substring(line.indexOf(": ") + 2);
                    //System.out.println(synonim);
                } else {
                    String line = lineFromFile;
                    synonim = "test";
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return synonim;
    }

    public static void readDocxFile(String fileName) {

        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();

            System.out.println("Total no of paragraph " + paragraphs.size());
            for (XWPFParagraph para : paragraphs) {
                doYourJob(para.getText());
//                System.out.println(para.getText());

            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}