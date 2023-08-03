package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.type.ComplexType;
import de.mlo.dev.tsbuilder.elements.type.TsSimpleType;
import de.mlo.dev.tsbuilder.elements.type.TsTypes;
import de.mlo.dev.tsbuilder.elements.values.Literal;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <pre>
 * param: string;
 * optionalParam?: string;
 * complexParam: {attribute1: string, attribute2: number}
 * </pre>
 */
@EqualsAndHashCode(callSuper = false)
@Getter
public class TsFunctionParameter extends TsElement<TsFunctionParameter> {

    private final String name;
    private TsElement<?> type;
    private boolean optional;

    public TsFunctionParameter(String name) {
        this.name = name;
    }

    public static TsFunctionParameter any(String name){
        return new TsFunctionParameter(name).setType(TsTypes.ANY);
    }

    public static TsFunctionParameter optionalAny(String name){
        return any(name).setOptional();
    }

    public static TsFunctionParameter anyArray(String name){
        return new TsFunctionParameter(name).setType(TsTypes.ANY_ARRAY);
    }

    public static TsFunctionParameter optionalAnyArray(String name){
        return anyArray(name).setOptional();
    }

    public static TsFunctionParameter string(String name){
        return new TsFunctionParameter(name).setType(TsTypes.STRING);
    }

    public static TsFunctionParameter optionalString(String name){
        return string(name).setOptional();
    }

    public static TsFunctionParameter stringArray(String name) {
        return new TsFunctionParameter(name)
                .setType(TsTypes.STRING_ARRAY);
    }

    public static TsFunctionParameter optionalStringArray(String name){
        return stringArray(name).setOptional();
    }

    public static TsFunctionParameter number(String name){
        return new TsFunctionParameter(name).setType(TsTypes.NUMBER);
    }

    public static TsFunctionParameter optionalNumber(String name){
        return number(name).setOptional();
    }

    public static TsFunctionParameter numberArray(String name){
        return new TsFunctionParameter(name).setType(TsTypes.NUMBER_ARRAY);
    }

    public static TsFunctionParameter optionalNumberArray(String name){
        return numberArray(name).setOptional();
    }

    public static TsFunctionParameter date(String name){
        return new TsFunctionParameter(name).setType(TsTypes.DATE);
    }

    public static TsFunctionParameter optionalDate(String name){
        return date(name).setOptional();
    }

    public static TsFunctionParameter dateArray(String name){
        return new TsFunctionParameter(name).setType(TsTypes.DATE_ARRAY);
    }

    public static TsFunctionParameter optionalDateArray(String name){
        return dateArray(name).setOptional();
    }

    public static TsFunctionParameter complex(String name, ComplexType complexType){
        return new TsFunctionParameter(name).setComplexType(complexType);
    }

    public static TsFunctionParameter optionalComplex(String name, ComplexType complexType){
        return complex(name, complexType).setOptional();
    }

    public static TsFunctionParameter custom(String name, String type){
        return new TsFunctionParameter(name).setType(type);
    }

    public static TsFunctionParameter optionalCustom(String name, String type){
        return custom(name, type).setOptional();
    }

    public static TsFunctionParameter customArray(String name, String type){
        return new TsFunctionParameter(name).setType(new TsSimpleType(type).toArray());
    }

    public static TsFunctionParameter optionalCustomArray(String name, String type){
        return customArray(name, type).setOptional();
    }

    public TsFunctionParameter setType(String parameterType) {
        return setType(new Literal(parameterType));
    }

    public TsFunctionParameter setType(TsElement<?> type) {
        this.type = type;
        return this;
    }

    public TsFunctionParameter setComplexType(ComplexType complexType){
        return this.setType(complexType);
    }

    public TsFunctionParameter setOptional(){
        return setOptional(true);
    }

    public TsFunctionParameter setOptional(boolean optional){
        this.optional = optional;
        return this;
    }

    @Override
    public TsElementWriter<TsFunctionParameter> createWriter(TsContext context) {
        return new TsFunctionParameterWriter(context, this);
    }


}
