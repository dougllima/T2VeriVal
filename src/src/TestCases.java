package src;

import java.io.*;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Criado por douglas.leite Parte do projeto T2VeriVal
 * <p>
 * 25/05/2017.
 */

public class TestCases {
    List<String[]> cases = new ArrayList<>();

    public TestCases(String fileName) throws Exception {
        BufferedReader br = null;

        try {
            if(fileName.isEmpty())
                throw new InvalidParameterException("Nome do arquivo não informado");

            File f = new File(Paths.get("").toAbsolutePath().toString() + "/");
            File[] matchingFiles = f.listFiles((dir, name) -> name.contains(fileName) && name.endsWith("txt"));

            if ((matchingFiles != null ? matchingFiles.length : 0) > 0) {
                br = new BufferedReader(new FileReader(matchingFiles[0].getPath()));

                String line;

                while ((line = br.readLine()) != null) {
                    int index = line.indexOf('[');
                    if (index > -1) {
                        String sub = line.substring(index);
                        if (!Objects.equals(sub, "")) {
                            sub = sub.replace("[", "");
                            sub = sub.replace("]", "");
                            sub = sub.replace(" ", "");
                            cases.add(sub.split(","));
                        }
                    }
                }
            } else
                throw new FileNotFoundException("Arquivo .txt não localizado");
        } catch (Exception e) {
            throw e;
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
