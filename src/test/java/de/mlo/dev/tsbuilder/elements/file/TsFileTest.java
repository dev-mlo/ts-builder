package de.mlo.dev.tsbuilder.elements.file;

import de.mlo.dev.tsbuilder.elements.clazz.TsClass;
import de.mlo.dev.tsbuilder.elements.clazz.field.TsField;
import de.mlo.dev.tsbuilder.elements.function.TsFunction;
import de.mlo.dev.tsbuilder.elements.function.TsFunctionParameter;
import de.mlo.dev.tsbuilder.elements.function.TsFunctionReturnType;
import de.mlo.dev.tsbuilder.elements.function.TsMethod;
import de.mlo.dev.tsbuilder.elements.interfaces.TsInterface;
import de.mlo.dev.tsbuilder.elements.interfaces.TsMethodDeclaration;
import de.mlo.dev.tsbuilder.elements.type.ComplexType;
import de.mlo.dev.tsbuilder.elements.type.TsType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TsFileTest {

    @Test
    void test(){
        TsMethodDeclaration loginMethodDeclaration = new TsMethodDeclaration("login")
                .addStringParameter("user")
                .addStringParameter("pw")
                .addReturnType(new TsFunctionReturnType("Observable<LoginState>"));
        TsInterface loginServiceInterface = new TsInterface("ILoginService")
                .setExport()
                .addMethod(loginMethodDeclaration);

        TsFile file = new TsFile("examples.ts")
                .addType(new TsType("LoginState")
                        .setExport()
                        .setValue(new ComplexType()
                                .addStringAttribute("username")
                                .addStringAttribute("token")))
                .addInterface(loginServiceInterface)
                .addClass(new TsClass("LoginService")
                        .addImplements(loginServiceInterface)
                        .setExport()
                        .addMethod(TsMethod.fromDeclaration(loginMethodDeclaration)
                                .addContent("// do login stuff"))
                        .addMethod(new TsMethod("logout")
                                .addContent("// do logout")));

        String result = file.buildWithImports();

        assertThat(result).isEqualTo("""
                export type LoginState = {
                  username: string,
                  token: string
                }
                export interface ILoginService{
                  login(user: string, pw: string): Observable<LoginState>;
                }
                export class LoginService implements ILoginService{
                  login (user: string, pw: string): Observable<LoginState> {
                    // do login stuff
                  }
                  logout () {
                    // do logout
                  }
                }""");
    }

    @Test
    void create_empty_file(){
        TsFile file = new TsFile("examples.ts");

        String result = file.buildWithImports();

        assertThat(result.trim()).isEmpty();
    }

    /**
     * <p>
     * It is possible to declare two files separately and merge them together.
     * Contents from both files will be taken over.
     * </p>
     * <p>
     * If both files declare the same class the content of the two class
     * will also be merged together.
     * </p>
     * <p>
     * If both classes declare the same methods the two methods will be
     * merged together as well.
     * </p>
     *
     * File 1: example.ts
     * <pre>{@code
     * class ExampleClass(){
     *     // Comment 1
     *     myMethod(){
     *         // Method comment 1
     *     }
     * }
     * }</pre>
     *
     * File 2: example.ts
     * <pre>{@code
     * class ExampleClass(){
     *     // Comment 2
     *     myMethod(){
     *         // Method comment 2
     *     }
     * }
     * }</pre>
     *
     * Becomes:
     * <pre>{@code
     * class ExampleClass(){
     *     // Comment 1
     *     myMethod(){
     *         // Method comment 1
     *         // Method comment 2
     *     }
     *     // Comment 2
     * }
     * }</pre>
     */
    @Test
    void merge_files(){
        TsFile firstFile = new TsFile("example.ts")
                .addFunction(new TsFunction("calculate")
                        .addComment("Global function comment 1"))
                .addFunction(new TsFunction("calculateFirstFile"))
                .addClass(new TsClass("ExampleClass")
                        .addFields(TsField.string("value"))
                        .addFields(TsField.string("valueFromFirstClass"))
                        .addMethod(new TsMethod("myMethod")
                                .addComment("Comment 1")));

        TsFile secondFile = new TsFile("example.ts")
                .addFunction(new TsFunction("calculate")
                        .addComment("Global function comment 2"))
                .addFunction(new TsFunction("calculateSecondFile"))
                .addClass(new TsClass("ExampleClass")
                        .addFields(TsField.string("value"))
                        .addFields(TsField.string("valueFromSecondClass"))
                        .addMethod(new TsMethod("myMethod")
                                .addComment("Comment 2")));

        TsFile merged = firstFile.merge(secondFile);
        String result = merged.build();

        assertThat(result).isEqualTo("""
                function calculate () {
                  // Global function comment 1
                  // Global function comment 2
                }
                function calculateFirstFile () {
                }
                class ExampleClass{
                  value: string;
                  valueFromFirstClass: string;
                  myMethod () {
                    // Comment 1
                    // Comment 2
                  }
                  valueFromSecondClass: string;
                }
                function calculateSecondFile () {
                }""");
    }

    @Test
    void compile_imports_from_functions(){
        TsFunction calculateDistance = new TsFunction("calculateDistance")
                .addParameter(TsFunctionParameter.number("from"))
                .addParameter(TsFunctionParameter.number("to"))
                .addReturnType(TsFunctionReturnType.literal("Distance"))
                .addContent("// Calculate some stuff");
        calculateDistance.addImport("Distance", "./my-types");

        TsFunction convertMilesToKilometer = new TsFunction("convertMilesToKilometer")
                .addParameter(TsFunctionParameter.custom("distance", "Miles"))
                .addReturnType(TsFunctionReturnType.literal("Kilometers"))
                .addContent("// Calculate some stuff");
        convertMilesToKilometer.addImport("Miles", "./my-types");
        convertMilesToKilometer.addImport("Kilometers", "./my-types");

        String result = new TsFile("calc.ts")
                .addFunction(calculateDistance)
                .addFunction(convertMilesToKilometer)
                .buildWithImports();

        assertThat(result).isEqualTo("""
                import {Distance, Miles, Kilometers} from './my-types';
                                
                function calculateDistance (from: number, to: number): Distance {
                  // Calculate some stuff
                }
                function convertMilesToKilometer (distance: Miles): Kilometers {
                  // Calculate some stuff
                }""");
    }
}