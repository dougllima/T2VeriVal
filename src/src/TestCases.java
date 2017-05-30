package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by douglas.leite on 25/05/2017.
 */

class TestCases {
    List<String[]> cases = new ArrayList<>();

    TestCases(String fileName) {
        BufferedReader br = null;
        FileReader fr = null;

        try {
            fr = new FileReader("tests/SequenciasDeTeste-" + fileName + ".txt");
            br = new BufferedReader(new FileReader("tests/SequenciasDeTeste-" + fileName + ".txt"));

            String line;

            while ((line = br.readLine()) != null) {
                int index = line.indexOf('[');
                String sub = line.substring(index);
                sub = sub.replace("[","");
                sub = sub.replace("]","");
                sub = sub.replace(" ","");
                cases.add(sub.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String args[]) {
        TestCases cases = new TestCases("vendingMachine");

        for (String[] s : cases.cases) {
            System.out.println(Arrays.toString(s));
        }
    }
}
