package de.mlo.dev.tsbuilder.elements.clazz.constructor;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TsConstructorParameterTest {

    @Test
    void parameter_without_type_definition(){
        TsConstructorParameter param = new TsConstructorParameter("param");

        String result = param.build();

        assertThat(result).isEqualTo("param: any");
    }

    @Test
    void create_string_parameter(){
        TsConstructorParameter parameter = TsConstructorParameter.string("param");

        String result = parameter.build();

        assertThat(result).isEqualTo("param: string");
    }

    @Test
    void create_optional_string_parameter(){
        TsConstructorParameter parameter = TsConstructorParameter.optionalString("param");

        String result = parameter.build();

        assertThat(result).isEqualTo("param?: string");
        assertThat(parameter.isOptional()).isTrue();
    }

    @Test
    void create_string_array_parameter(){
        TsConstructorParameter parameter = TsConstructorParameter.stringArray("param");

        String result = parameter.build();

        assertThat(result).isEqualTo("param: string[]");
    }

    @Test
    void create_parameter_with_different_types(){
        TsConstructorParameter parameter = TsConstructorParameter.string("name")
                .addOrType("Name")
                .setNullable();

        String result = parameter.build();

        assertThat(result).isEqualTo("name: string | Name | null");
    }
}