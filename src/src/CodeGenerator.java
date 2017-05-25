package src;

import com.squareup.javapoet.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.IOException;

/**
 * Created by douglas.leite on 23/05/2017.
 * <p>
 * Classe para gerar o código executavel atravez da biblioteca JavaPoet
 */
class CodeGenerator {
    MealyMachine machine;


    static void makeItRain(MealyMachine machine, String fileName) {
        try {
            MethodSpec main = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();

            TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(main)
                    .build();

            JavaFile javaFile = JavaFile.builder("src", helloWorld)
                    .build();

            javaFile.writeTo(new File(""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
