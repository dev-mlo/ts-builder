package de.mlo.dev.tsbuilder.elements.clazz.field;

import de.mlo.dev.tsbuilder.elements.MergeException;
import de.mlo.dev.tsbuilder.elements.clazz.TsClass;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecorator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TsFieldTest {

    @Test
    void create_setter() {
        TsField field = TsField.optionalString("value")
                .addSetter();

        String result = field.build();

        assertThat(result).isEqualTo("""
                _value?: string;
                set value (value?: string) {
                  this._value = value;
                }""");
    }

    @Test
    void create_setter_with_decorator(){
        TsField field = TsField.optionalString("value")
                .addSetter(new TsDecorator("Input"));

        String result = field.build();

        assertThat(result).isEqualTo("""
                _value?: string;
                @Input()
                set value (value?: string) {
                  this._value = value;
                }""");
    }

    @Test
    void create_getter() {
        TsField field = TsField.optionalString("value")
                .addGetter();

        String result = field.build();

        assertThat(result).isEqualTo("""
                _value?: string;
                get value (): string | undefined {
                  return this._value;
                }""");
    }

    @Test
    void create_setter_and_getter(){
        TsField field = TsField.optionalString("value")
                .addSetter()
                .addGetter();

        String result = field.build();

        assertThat(result).isEqualTo("""
                _value?: string;
                set value (value?: string) {
                  this._value = value;
                }
                get value (): string | undefined {
                  return this._value;
                }""");
    }

    @Test
    void merge_duplicated_fields_in_a_class(){
        TsClass clazz = new TsClass("MyClass")
                .addField(TsField.string("foo", "bar"))
                .addField(TsField.string("foo", "bar"));

        String result = clazz.build();

        System.out.println(result);

        assertThat(result).isEqualTo("""
            class MyClass{
              foo: string = 'bar';
            }""");
    }

    @Test
    void merge_duplicated_fields_in_a_class_with_different_signatures(){
        TsClass clazz = new TsClass("MyClass")
                .addField(TsField.string("foo", "bar"))
                .addField(TsField.number("foo", "1"));

        assertThatThrownBy(clazz::build)
                .isInstanceOf(MergeException.class);
    }
}