package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Criado por douglas.leite
 * Parte do projeto T2VeriVal
 *
 * 25/05/2017.
 */

class TestCases {
    List<String[]> cases = new ArrayList<>();

    TestCases(String fileName) {
        BufferedReader br = null;

        try {
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
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
