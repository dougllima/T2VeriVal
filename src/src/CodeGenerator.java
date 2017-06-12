package src;

import com.squareup.javapoet.*;
import org.junit.*;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
            String varName = fileName.toLowerCase();

            ClassName className = ClassName.get("", fileName);

            MethodSpec beforeMethod = MethodSpec.methodBuilder("init")
                    .addAnnotation(Before.class)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(void.class)
                    .addStatement("$T " + varName + " = new $T()", className, className)
                    .addStatement("//Implemente o metodo setup aqui!")
                    .build();

            TypeSpec.Builder classSpec = TypeSpec.classBuilder(fileName + "Test")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            classSpec.addMethod(beforeMethod);

            // TODO - Adicionar os outros testes conforme a maquina.

            Status actualState = machine.statuses.get(machine.initialStatus);

            int cont = 1;
            for (String[] test : tests.cases) {
                MethodSpec.Builder testMethod = MethodSpec.methodBuilder("testCase" + cont)
                        .addAnnotation(Test.class)
                        .returns(void.class);

                for (String s : test) {
                    Transition transition = machine.transitions.get(actualState.id).get(s+"()");
                    if (transition != null)
                        actualState = transition.to;

                    testMethod.addStatement("assertEquals($S(), " + transition.transout + ")", s);
                }

                classSpec.addMethod(testMethod.build());
            }

            JavaFile javaFile = JavaFile.builder("", classSpec.build())
                    .indent("\t") //Indenta o código com Tab
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
