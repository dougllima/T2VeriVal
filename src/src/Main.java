package src;

public class Main {
    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator(args.length < 1 ? "VendingMachine" : args[0]);
        generator.makeItRain();
    }
}
