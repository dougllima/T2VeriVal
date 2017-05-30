package src;

import com.squareup.javapoet.*;
import org.xml.sax.SAXException;

import javax.lang.model.element.Modifier;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by douglas.leite on 23/05/2017.
 * <p>
 * Classe para gerar o código executavel atravez da biblioteca JavaPoet
 */
class CodeGenerator {
    String fileName;
    MealyMachine machine;
    TestCases tests;

    CodeGenerator(String fileName) {
        try {
            this.fileName = fileName;
            this.machine = XMLParser.createMachine(fileName);
            this.tests = new TestCases(fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void makeItRain() {
        try {
            MethodSpec methodSpec = MethodSpec.methodBuilder("main")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(void.class)
                    .addParameter(String[].class, "args")
                    .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                    .build();

            TypeSpec.Builder classSpec2 = TypeSpec.classBuilder(fileName + "Test")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(methodSpec);

            TypeSpec classSpec = classSpec2
                    .addMethod(methodSpec)
                    .build();

            JavaFile javaFile = JavaFile.builder("", classSpec)
                    .build();

            File f = new File(fileName);
            if (!f.exists())
                if (!f.mkdir())
                    throw new Exception("Não foi possivel criar o diretorio informado");
            javaFile.writeTo(f);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
