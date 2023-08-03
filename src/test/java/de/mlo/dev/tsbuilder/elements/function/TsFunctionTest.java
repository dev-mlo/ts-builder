package de.mlo.dev.tsbuilder.elements.function;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TsFunctionTest {

    @Test
    void create_hello_world(){
        TsFunction function = new TsFunction("printHelloWorld")
                .setExportDefault()
                .addContent("console.log('Hello World');");

        String result = function.build();

        assertThat(result).isEqualTo("""
                export default function printHelloWorld () {
                  console.log('Hello World');
                }""");
    }

    @Test
    void check_merge_required_of_same_function_names(){
        TsFunction first = new TsFunction("fn");
        TsFunction second = new TsFunction("fn");
        boolean mergeRequired = first.isMergeRequired(second);
        assertThat(mergeRequired).isTrue();
    }

    @Test
    void check_merge_required_of_different_function_names(){
        TsFunction first = new TsFunction("fn1");
        TsFunction second = new TsFunction("fn2");
        boolean mergeRequired = first.isMergeRequired(second);
        assertThat(mergeRequired).isFalse();
    }

    @Test
    void check_merge_required_of_getter_and_setter(){
        TsFunction first = new TsFunction("fn")
                .setSetter();
        TsFunction second = new TsFunction("fn")
                .setGetter();
        boolean mergeRequired = first.isMergeRequired(second);
        assertThat(mergeRequired).isFalse();
    }

    @Test
    void check_merge_required_of_two_getters(){
        TsFunction first = new TsFunction("fn")
                .setGetter();
        TsFunction second = new TsFunction("fn")
                .setGetter();
        boolean mergeRequired = first.isMergeRequired(second);
        assertThat(mergeRequired).isTrue();
    }

    @Test
    void merge_two_function(){
        TsFunction first = new TsFunction("fn")
                .addContent("// Comment 1");
        TsFunction second = new TsFunction("fn")
                .addContent("// Comment 2");

        TsFunction mergedFunction = first.mergeContent(second);
        String result = mergedFunction.build();

        assertThat(result).isEqualTo("""
                function fn () {
                  // Comment 1
                  // Comment 2
                }""");
    }


}