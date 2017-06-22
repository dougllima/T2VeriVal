package tests;

import org.junit.Test;
import src.TestCases;

import java.io.FileNotFoundException;
import java.security.InvalidParameterException;

import static org.junit.Assert.*;

/**
 * @author Douglas Lima
 *         Parte do projeto T2VeriVal
 *         <p>
 *         22/06/2017.
 */
public class TestCasesTest {
    TestCases tc;

    @Test
    public void TestCasesValidFile() throws Exception {
        tc = new TestCases("teste");
    }

    @Test(expected = FileNotFoundException.class)
    public void TestCasesInvalidFile() throws Exception {
        tc = new TestCases("b");
    }

    @Test(expected = InvalidParameterException.class)
    public void TestCasesNoFile() throws Exception {
        tc = new TestCases("");
    }
}