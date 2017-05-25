package src;

public class Main {
    public static void main(String[] args) {
        try {
            MealyMachine machine = XMLParser.createMachine("vendingMachine.jff");

            CodeGenerator.makeItRain(machine,"vendingMachine");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
