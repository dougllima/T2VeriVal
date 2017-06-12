package src;

public class Main {
    public static void main(String[] args) {
        try {
            CodeGenerator generator = new CodeGenerator("VendingMachine");
            generator.makeItRain();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
