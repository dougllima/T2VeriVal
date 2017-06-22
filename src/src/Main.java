package src;

import java.util.Scanner;

public class Main {
    static Scanner entrada;

    public Main(Scanner s) {
        entrada = s;
    }

    public static void main(String[] args) {
        try {
            if (args.length > 0) {
                CodeGenerator generator = new CodeGenerator(args[0]);
                generator.GenerateTests();
            } else {
                if (entrada == null)
                    entrada = new Scanner(System.in);
                System.out.println("Informe o nome do arquivo:");
                String fileName = entrada.nextLine();
                CodeGenerator generator = new CodeGenerator(fileName);
                generator.GenerateTests();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
