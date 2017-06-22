package tests;

import org.junit.Test;
import src.MealyMachine;

import java.io.FileNotFoundException;
import java.security.InvalidParameterException;

/**
 * @author Douglas Lima
 *         Parte do projeto T2VeriVal
 *         <p>
 *         22/06/2017.
 */
public class MealyMachineTest {
    MealyMachine machine;

    @Test
    public void populateMachineValidFile() throws Exception {
        machine = new MealyMachine("teste");
        machine.populateMachine();
    }

    @Test(expected = FileNotFoundException.class)
    public void populateMachineInvalidFile() throws Exception {
        machine = new MealyMachine("b");
        machine.populateMachine();
    }

    @Test(expected = InvalidParameterException.class)
    public void populateMachineNoFile() throws Exception {
        machine = new MealyMachine("");
        machine.populateMachine();
    }
}