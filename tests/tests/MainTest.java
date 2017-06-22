package tests;

import org.junit.Test;
import src.Main;

import java.util.Scanner;

/**
 * @author Douglas Lima
 *         Parte do projeto T2VeriVal
 *         <p>
 *         22/06/2017.
 */
public class MainTest {
    Main main;

    @Test
    public void mainValid() throws Exception {
        Main.main(new String[]{"teste"});
    }

    @Test
    public void mainInvalid() throws Exception {
        Main.main(new String[]{"","teste"});
    }

    @Test
    public void mainNoFileValid() throws Exception {
        String input = "teste";
        main = new Main(new Scanner(input));
    }

    @Test
    public void mainNoFileInvalid() throws Exception {
        String input = "";
        main = new Main(new Scanner(input));
    }
}