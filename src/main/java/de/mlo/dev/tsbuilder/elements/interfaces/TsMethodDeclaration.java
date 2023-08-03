package de.mlo.dev.tsbuilder.elements.interfaces;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.common.TsModifier;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import de.mlo.dev.tsbuilder.elements.function.*;
import de.mlo.dev.tsbuilder.elements.type.ComplexType;
import de.mlo.dev.tsbuilder.elements.type.TsSimpleType;
import de.mlo.dev.tsbuilder.elements.type.TsTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;

@EqualsAndHashCode(callSuper = false)
@Getter
public class TsMethodDeclaration extends TsElement<TsMethodDeclaration> {

    private final TsModifierList modifierList = new TsModifierList();
    private final TsFunctionParameterList parameterList = new TsFunctionParameterList();
    private final TsFunctionReturnTypeList returnTypeList = new TsFunctionReturnTypeList();
    private final String name;

    public TsMethodDeclaration(String name) {
        this.name = name;
    }

    public static TsMethodDeclaration fromMethod(TsMethod method){
        return new TsMethodDeclaration(method.getName())
                .addModifiers(method.getModifierList())
                .addParameters(method.getParameterList())
                .addReturnTypes(method.getReturnTypeList());
    }

    public static TsMethodDeclaration fromFunction(TsFunction method){
        return new TsMethodDeclaration(method.getName())
                .addModifiers(method.getModifierList())
                .addParameters(method.getParameterList())
                .addReturnTypes(method.getReturnTypeList());
    }

    public TsMethodDeclaration addModifier(TsModifier modifier){
        this.modifierList.add(modifier);
        return this;
    }

    public TsMethodDeclaration addModifiers(Collection<TsModifier> modifiers){
        this.modifierList.addAll(modifiers);
        return this;
    }

    public TsMethodDeclaration addAnyParameter(String name){
        return addParameter(TsFunctionParameter.any(name));
    }

    public TsMethodDeclaration addOptionalAnyParameter(String name){
        return addParameter(TsFunctionParameter.optionalAny(name));
    }

    public TsMethodDeclaration addAnyArrayParameter(String name){
        return addParameter(TsFunctionParameter.anyArray(name));
    }

    public TsMethodDeclaration addOptionalAnyArrayParameter(String name){
        return addParameter(TsFunctionParameter.optionalAnyArray(name));
    }

    public TsMethodDeclaration addStringParameter(String name){
        return addParameter(TsFunctionParameter.string(name));
    }

    public TsMethodDeclaration addOptionalStringParameter(String name){
        return addParameter(TsFunctionParameter.optionalString(name));
    }

    public TsMethodDeclaration addStringArrayParameter(String name){
        return addParameter(TsFunctionParameter.stringArray(name));
    }

    public TsMethodDeclaration addOptionalStringArrayParameter(String name){
        return addParameter(TsFunctionParameter.optionalStringArray(name));
    }

    public TsMethodDeclaration addNumberParameter(String name){
        return addParameter(TsFunctionParameter.number(name));
    }

    public TsMethodDeclaration addOptionalNumberParameter(String name){
        return addParameter(TsFunctionParameter.optionalNumber(name));
    }

    public TsMethodDeclaration addArrayNumberParameter(String name){
        return addParameter(TsFunctionParameter.numberArray(name));
    }

    public TsMethodDeclaration addOptionalNumberArrayParameter(String name){
        return addParameter(TsFunctionParameter.optionalNumberArray(name));
    }

    public TsMethodDeclaration addDateParameter(String name){
        return addParameter(TsFunctionParameter.date(name));
    }

    public TsMethodDeclaration addOptionalDateParameter(String name){
        return addParameter(TsFunctionParameter.optionalDate(name));
    }

    public TsMethodDeclaration addDateArrayParameter(String name){
        return addParameter(TsFunctionParameter.dateArray(name));
    }

    public TsMethodDeclaration addOptionalDataArrayParameter(String name){
        return addParameter(TsFunctionParameter.optionalDateArray(name));
    }

    public TsMethodDeclaration addComplexParameter(String name, ComplexType complexType){
        return addParameter(TsFunctionParameter.complex(name, complexType));
    }

    public TsMethodDeclaration addOptionalComplexParameter(String name, ComplexType complexType){
        return addParameter(TsFunctionParameter.optionalComplex(name, complexType));
    }

    public TsMethodDeclaration addCustomParameter(String name, String type){
        return addParameter(TsFunctionParameter.custom(name, type));
    }

    public TsMethodDeclaration addOptionalCustomParameter(String name, String type){
        return addParameter(TsFunctionParameter.optionalCustom(name, type));
    }

    public TsMethodDeclaration addCustomArrayParameter(String name, String type){
        return addParameter(TsFunctionParameter.customArray(name, type));
    }

    public TsMethodDeclaration addOptionalCustomArrayParameter(String name, String type){
        return addParameter(TsFunctionParameter.optionalCustomArray(name, type));
    }

    public TsMethodDeclaration addParameter(TsFunctionParameter parameter) {
        this.parameterList.add(parameter);
        return this;
    }

    public TsMethodDeclaration addParameters(Collection<TsFunctionParameter> parameters) {
        this.parameterList.addAll(parameters);
        return this;
    }

    public TsMethodDeclaration addStringReturnType(){
        return addReturnType(TsTypes.STRING);
    }

    public TsMethodDeclaration addStringArrayReturnType(){
        return addReturnType(TsTypes.STRING_ARRAY);
    }

    public TsMethodDeclaration addNumberReturnType(){
        return addReturnType(TsTypes.NUMBER);
    }

    public TsMethodDeclaration addNumberArrayReturnType(){
        return addReturnType(TsTypes.NUMBER_ARRAY);
    }

    public TsMethodDeclaration addCustomReturnType(String name){
        return addReturnType(new TsSimpleType(name));
    }

    public TsMethodDeclaration addCustomArrayReturnType(String name){
        return addReturnType(new TsSimpleType(name).toArray());
    }

    public TsMethodDeclaration addReturnType(TsElement<?> element){
        this.returnTypeList.add(element);
        return this;
    }

    public TsMethodDeclaration addReturnType(TsFunctionReturnType element){
        addReturnType((TsElement<?>) element);
        return this;
    }

    public TsMethodDeclaration addReturnTypes(Collection<? extends TsElement<?>> elements){
        this.returnTypeList.addAll(elements);
        return this;
    }

    @Override
    public TsElementWriter<TsMethodDeclaration> createWriter(TsContext context) {
        return new TsMethodDeclarationWriter(context, this);
    }

}
