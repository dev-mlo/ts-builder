package de.mlo.dev.tsbuilder.elements;

import de.mlo.dev.tsbuilder.elements.clazz.TsClass;
import de.mlo.dev.tsbuilder.elements.clazz.constructor.TsConstructor;
import de.mlo.dev.tsbuilder.elements.clazz.field.TsField;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecorator;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecoratorProperty;
import de.mlo.dev.tsbuilder.elements.function.TsMethod;
import de.mlo.dev.tsbuilder.elements.values.ComplexValue;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TsClassTest {

    @Test
    void test() {
        TsClass tsClass = new TsClass("MyClass")
                .setExport()
                .addContent(new TsMethod("ping")
                        .setPublic()
                        .addContent("console.log('pong');"));

        String result = tsClass.build();

        assertThat(result).isEqualTo("""
                export class MyClass{
                  public ping () {
                    console.log('pong');
                  }
                }""");
    }

    @Test
    void class_with_interface() {
        TsClass clazz = new TsClass("MyClass")
                .addImplements("MyInterface");

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass implements MyInterface{
                }""");
    }

    @Test
    void class_with_superclass() {
        TsClass clazz = new TsClass("MyClass")
                .setSuperClass("MySuperClass");

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass extends MySuperClass{
                }""");
    }

    @Test
    void class_with_superclass_and_interfaces() {
        TsClass clazz = new TsClass("MyClass")
                .setSuperClass("MySuperClass")
                .addImplements("MyInterface")
                .addImplements("OtherInterface");

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass extends MySuperClass implements MyInterface, OtherInterface{
                }""");
    }

    @Test
    void class_with_empty_constructor() {
        TsClass clazz = new TsClass("MyClass")
                .setConstructor(new TsConstructor());

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  constructor () {
                  }
                }""");
    }

    @Test
    void class_with_constructor() {
        TsClass clazz = new TsClass("MyClass")
                .setConstructor(new TsConstructor()
                        .addStringParameter("stringParam")
                        .addStringArrayParameter("stringArrayParam")
                        .addOptionalStringParameter("optionalStringParam"));

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  constructor (stringParam: string, stringArrayParam: string[], optionalStringParam?: string) {
                  }
                }""");
    }

    @Test
    void class_with_constructor_with_content() {
        TsClass clazz = new TsClass("MyClass")
                .setConstructor(new TsConstructor()
                        .addStringParameter("stringParam")
                        .addContent("console.log(stringParam);"));

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  constructor (stringParam: string) {
                    console.log(stringParam);
                  }
                }""");

    }

    @Test
    void add_string_field() {
        TsClass clazz = new TsClass("MyClass")
                .addStringField("myString", "stringContent");

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  myString: string = 'stringContent';
                }""");
    }

    @Test
    void add_string_array_field() {
        TsClass clazz = new TsClass("MyClass")
                .addStringArrayField("colors", "blue", "red", "green");

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  colors: string[] = ['blue', 'red', 'green'];
                }""");
    }

    @Test
    void add_method_content() {
        TsClass clazz = new TsClass("MyClass")
                .addMethod(new TsMethod("foo")
                        .addContent("// Comment 1"));

        String result = clazz.build();
        assertThat(result).isEqualTo("""
                class MyClass{
                  foo () {
                    // Comment 1
                  }
                }""");

        clazz.addMethodContent("foo", TsElement.literal("// Comment 2"));

        result = clazz.build();
        assertThat(result).isEqualTo("""
                class MyClass{
                  foo () {
                    // Comment 1
                    // Comment 2
                  }
                }""");
    }

    @Test
    void merge_classes() {
        // FIXME
    }

    @Test
    void merge_classes_with_imports(){
        // FIXME
    }

    @Test
    void create_ng_component(){

        // Creating the component decorator which
        TsDecorator componentDecorator = new TsDecorator("Component")
                .addProperty(new TsDecoratorProperty().setValue(new ComplexValue()
                        .addStringValue("selector", "app-my-component[user]")
                        .addStringValue("templateUrl", "./my.component.html")
                        .addStringArrayValue("styleUrls", "./my.component.scss")));
        componentDecorator.addImport("Component", "@angular/core");

        // Add a field which holds the current user and provide a setter function
        TsField userField = TsField.optionalCustom("user", "User")
                .addSetter(new TsDecorator("Input"));
        userField.addImport("Input", "@angular/core");

        // Define a class and add the decorator and the defined field
        TsClass clazz = new TsClass("MyComponent")
                .setExport()
                .addDecorator(componentDecorator)
                .addField(userField);

        String result = clazz.buildWithImports();

        System.out.println(result);
    }
}