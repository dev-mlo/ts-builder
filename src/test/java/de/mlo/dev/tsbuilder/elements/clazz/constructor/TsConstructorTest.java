package de.mlo.dev.tsbuilder.elements.clazz.constructor;

import de.mlo.dev.tsbuilder.elements.type.ComplexType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TsConstructorTest {

    @Test
    void create_empty_constructor() {
        TsConstructor constructor = new TsConstructor();

        String result = constructor.build();

        assertThat(result).isEqualTo("""
                constructor (){
                }""");
    }

    @Test
    void create_constructor_with_one_parameter() {
        TsConstructor constructor = new TsConstructor()
                .addStringParameter("name");

        String result = constructor.build();

        assertThat(result).isEqualTo("""
                constructor (name: string){
                }""");
    }

    @Test
    void create_constructor_with_multiple_parameter() {
        TsConstructor constructor = new TsConstructor()
                .addStringParameter("name")
                .addStringParameter("surname");

        String result = constructor.build();

        assertThat(result).isEqualTo("""
                constructor (name: string, surname: string){
                }""");
    }

    @Test
    void create_constructor_with_optional_parameter() {
        TsConstructor constructor = new TsConstructor()
                .addOptionalStringParameter("name")
                .addStringParameter("surname");

        String result = constructor.build();

        assertThat(result).isEqualTo("""
                constructor (name?: string, surname: string){
                }""");
    }

    @Test
    void create_constructor_with_complex_parameter() {
        TsConstructor constructor = new TsConstructor()
                .addParameter(TsConstructorParameter.complex("name", new ComplexType()
                        .addStringAttribute("firstname")
                        .addStringAttribute("surname")));

        String result = constructor.build();

        assertThat(result).isEqualTo("""
                constructor (name: {
                  firstname: string,
                  surname: string
                }){
                }""");
    }

    @Test
    void create_constructor_with_optional_complex_parameter() {
        TsConstructor constructor = new TsConstructor()
                .addParameter(TsConstructorParameter.optionalComplex("name", new ComplexType()
                        .addStringAttribute("firstname")
                        .addStringAttribute("surname")));

        String result = constructor.build();

        assertThat(result).isEqualTo("""
                constructor (name?: {
                  firstname: string,
                  surname: string
                }){
                }""");
    }
}