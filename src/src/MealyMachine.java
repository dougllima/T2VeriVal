package src;

import com.squareup.javapoet.*;

import src.MealyMachine.State;
import src.MealyMachine.Transition;

import org.junit.*;
import org.junit.internal.runners.JUnit38ClassRunner;

import javax.lang.model.element.Modifier;
import java.io.File;
import java.util.Arrays;

/**
 * Created by douglas.leite on 23/05/2017.
 * <p>
 * Classe para gerar o cÃ³digo executavel atravez da biblioteca JavaPoet
 */
class CodeGenerator {
	String fileName;
	MealyMachine machine;
	TestCases tests;

	CodeGenerator(String fileName) {
		try {
			this.fileName = fileName;
			this.machine = new MealyMachine(fileName);
			this.tests = new TestCases(fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void makeItRain() {
		try {
			String varName = fileName.toLowerCase();

			ClassName className = ClassName.get("", fileName);

			MethodSpec beforeMethod = MethodSpec.methodBuilder("init").addAnnotation(Before.class)
					.addModifiers(Modifier.PUBLIC).returns(void.class).addStatement("$L = new $T()", varName, className)
					.build();

			TypeSpec.Builder classSpec = TypeSpec.classBuilder(fileName + "Test").addField(className, varName)
					.addModifiers(Modifier.PUBLIC, Modifier.FINAL);

			classSpec.addMethod(beforeMethod);

			int cont = 1;
			for (String[] test : tests.cases) {
				State actualState = machine.statuses.get(machine.initialStatus);
				MethodSpec.Builder testMethod = MethodSpec.methodBuilder("testCase" + cont)
						.addModifiers(Modifier.PUBLIC)
						.addJavadoc("Caso de teste para a sequencia de entradas: \n$L\n", Arrays.toString(test))
						.addAnnotation(Test.class).returns(void.class);

				for (String s : test) {
					if (s.indexOf('(') == -1)
						s += "()";
					Transition transition = machine.transitions.get(actualState.id).get(s);
					if (transition != null) {
						actualState = transition.to;
						if (transition.transout != null)
							testMethod.addStatement("assertEquals($L.$L, $L)", varName, s, transition.transout);
						else
							testMethod.addStatement("$L.$L", varName, s);
					}
				}

				classSpec.addMethod(testMethod.build());
				cont++;
			}

			JavaFile javaFile = JavaFile.builder("", classSpec.build()).indent("\t")
					.addStaticImport(Assert.class,"*")
					.build();

			File f = new File(fileName);
			if (!f.exists())
				if (!f.mkdir())
					throw new Exception("NÃ£o foi possivel criar o diretorio informado");
			javaFile.writeTo(f);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
