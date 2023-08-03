package de.mlo.dev.tsbuilder.elements;

import de.mlo.dev.tsbuilder.elements.clazz.TsClass;
import de.mlo.dev.tsbuilder.elements.clazz.field.TsField;
import de.mlo.dev.tsbuilder.elements.function.TsFunction;
import de.mlo.dev.tsbuilder.elements.function.TsMethod;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ContentMergerTest {

    @Test
    void test(){
        TsFunction first = new TsFunction("fn")
                .addContent("// Comment 1");
        TsFunction second = new TsFunction("fn")
                .addContent("// Comment 2");

        TsElementList list = new TsElementList();
        list.addAll(List.of(first, second));

        TsElementList resolved = list.resolveDuplications();
        String result = resolved.build(new TsContext());

        System.out.println(result);
    }

    @Test
    void class_with_duplicated_methods(){
        TsClass clazz = new TsClass("MyClass")
                .addMethod(new TsMethod("foo")
                        .addContent("// Comment 1"))
                .addMethod(new TsMethod("foo")
                        .addContent("// Comment 2"));

        String result = clazz.build();

        Assertions.assertThat(result).isEqualTo("""
                class MyClass{
                  foo () {
                    // Comment 1
                    // Comment 2
                  }
                }""");
    }

    @Test
    void class_with_duplicated_fields(){
        TsClass clazz = new TsClass("MyClass")
                .addField(TsField.string("foo", "bar"))
                .addField(TsField.string("foo", "bar"));

        String result = clazz.build();

        Assertions.assertThat(result).isEqualTo("""
                class MyClass{
                  foo: string = 'bar';
                }""");
    }
}