package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.elements.MergeException;
import de.mlo.dev.tsbuilder.elements.clazz.TsClass;
import org.junit.jupiter.api.Test;

import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TsMethodTest {

    @Test
    void merge_methods(){
        TsClass clazz = new TsClass("MyClass")
                .addMethod(new TsMethod("foo")
                        .addComment("First"))
                .addMethod(new TsMethod("foo")
                        .addComment("Second"));

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  foo () {
                    // First
                    // Second
                  }
                }""");
    }

    @Test
    void merge_methods_with_same_content(){
        TsClass clazz = new TsClass("MyClass")
                .addMethod(new TsMethod("foo")
                        .addComment("First"))
                .addMethod(new TsMethod("foo")
                        .addComment("First"));

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  foo () {
                    // First
                  }
                }""");
    }

    @Test
    void merge_parameter_mismatch(){
        TsClass clazz = new TsClass("MyClass")
                .addMethod(new TsMethod("foo")
                        .addParameter(TsFunctionParameter.string("bar"))
                        .addComment("First"))
                .addMethod(new TsMethod("foo")
                        .addComment("Second"));

        assertThatThrownBy(clazz::build)
                .isInstanceOf(MergeException.class);
    }

    /**
     * We do not remove duplicated lines in this case. The order of statements in this
     * case might be intentional.<br>
     * This behaviour can be changed by setting a custom resolver:
     * {@link TsMethod#setContentResolver(BiConsumer)} or use the existing on
     * {@link TsMethod#removeDuplicatedElementsOnMerge()}
     */
    @Test
    void merge_multiple_methods_with_duplicated_and_same_content(){
        TsClass clazz = new TsClass("MyClass")
                .addMethod(new TsMethod("foo")
                        .addComment("First"))
                .addMethod(new TsMethod("foo")
                        .addComment("Second"))
                .addMethod(new TsMethod("foo")
                        .addComment("Second"))
                .addMethod(new TsMethod("foo")
                        .addComment("First"))
                .addMethod(new TsMethod("foo")
                        .addComment("First"));

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  foo () {
                    // First
                    // Second
                    // Second
                    // First
                    // First
                  }
                }""");
    }

    @Test
    void merge_multiple_methods_with_duplicated_and_same_content_and_force_duplication_removal(){
        TsClass clazz = new TsClass("MyClass")
                .addMethod(new TsMethod("foo")
                        .removeDuplicatedElementsOnMerge()
                        .addComment("First"))
                .addMethod(new TsMethod("foo")
                        .addComment("Second"))
                .addMethod(new TsMethod("foo")
                        .addComment("Second"))
                .addMethod(new TsMethod("foo")
                        .addComment("First"))
                .addMethod(new TsMethod("foo")
                        .addComment("First"));

        String result = clazz.build();

        assertThat(result).isEqualTo("""
                class MyClass{
                  foo () {
                    // First
                    // Second
                  }
                }""");
    }
}