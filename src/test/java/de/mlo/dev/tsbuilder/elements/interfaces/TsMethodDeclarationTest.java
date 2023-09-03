package de.mlo.dev.tsbuilder.elements.interfaces;

import de.mlo.dev.tsbuilder.elements.function.TsFunction;
import de.mlo.dev.tsbuilder.elements.function.TsMethod;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TsMethodDeclarationTest {

    @Test
    void with_any_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addAnyParameter("anyParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(anyParam: any): void;");
    }

    @Test
    void with_optional_any_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addOptionalAnyParameter("anyParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(anyParam?: any): void;");
    }

    @Test
    void with_any_array_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addAnyArrayParameter("anyParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(anyParam: any[]): void;");
    }

    @Test
    void with_optional_any_array_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addOptionalAnyArrayParameter("anyParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(anyParam?: any[]): void;");
    }

    @Test
    void with_string_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addStringParameter("stringParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(stringParam: string): void;");
    }

    @Test
    void with_optional_string_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addOptionalStringParameter("stringParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(stringParam?: string): void;");
    }

    @Test
    void with_string_array_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addStringArrayParameter("stringParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(stringParam: string[]): void;");
    }

    @Test
    void with_optional_string_array_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addOptionalStringArrayParameter("stringParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(stringParam?: string[]): void;");
    }

    @Test
    void with_number_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addNumberParameter("numberParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(numberParam: number): void;");
    }

    @Test
    void with_optional_number_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addOptionalNumberParameter("numberParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(numberParam?: number): void;");
    }

    @Test
    void with_number_array_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addArrayNumberParameter("numberParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(numberParam: number[]): void;");
    }

    @Test
    void with_optional_number_array_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addOptionalNumberArrayParameter("numberParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(numberParam?: number[]): void;");
    }

    @Test
    void with_date_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addDateParameter("dateParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(dateParam: Date): void;");
    }

    @Test
    void with_optional_date_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addOptionalDateParameter("dateParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(dateParam?: Date): void;");
    }

    @Test
    void with_date_array_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addDateArrayParameter("dateParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(dateParam: Date[]): void;");
    }

    @Test
    void with_optional_date_array_parameter(){
        TsMethodDeclaration declaration = new TsMethodDeclaration("fn")
                .addOptionalDataArrayParameter("dateParam");

        String result = declaration.build();

        assertThat(result).isEqualTo("fn(dateParam?: Date[]): void;");
    }

    @Test
    void from_method(){
        TsMethodDeclaration methodDeclaration = TsMethodDeclaration.fromMethod(
                new TsMethod("login")
                        .addStringParameter("user")
                        .addStringParameter("password")
                        .addObservableReturnType("LoginState")
                        .addContent("//Login logic here"));

        String result = methodDeclaration.build();

        assertThat(result).isEqualTo("""
                login (user: string, password: string): Observable<LoginState>;""");
    }

    @Test
    void from_function(){
        TsMethodDeclaration methodDeclaration = TsMethodDeclaration.fromFunction(
                new TsFunction("login")
                        .addStringParameter("user")
                        .addStringParameter("password")
                        .addObservableReturnType("LoginState")
                        .addContent("//Login logic here"));

        String result = methodDeclaration.build();

        assertThat(result).isEqualTo("""
                login (user: string, password: string): Observable<LoginState>;""");
    }
}