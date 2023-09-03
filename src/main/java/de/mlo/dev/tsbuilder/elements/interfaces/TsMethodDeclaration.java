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

/**
 * The method declaration (or function declaration) is the signature of a method.
 * The signature contains the modifiers, the name, the parameters and
 * the return types of the method.<br>
 * Method declarations can be used to declare interfaces.<br>
 *
 * Example:
 * <pre>{@code login (user: string, password: string): Observable<LoginState>;}</pre>
 */
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

    /**
     * Extracts the signature of a {@link TsMethod} and creates a {@link TsMethodDeclaration} object
     * which can be used in {@link TsInterface} for example. This function extracts the current
     * state of the given method. Further modification to the given method object will not be
     * affect the created declaration object.<br>
     * The following parts will be used for the declaration object:
     * <ol>
     *     <li>The name of the method</li>
     *     <li>The modifiers</li>
     *     <li>The parameters</li>
     *     <li>The return types</li>
     * </ol>
     *
     * Example:
     * <pre>{@code
     * TsMethodDeclaration methodDeclaration = TsMethodDeclaration.fromMethod(
     *               new TsMethod("login")
     *                       .addStringParameter("user")
     *                       .addStringParameter("password")
     *                       .addObservableReturnType("LoginState")
     *                       .addContent("//Login logic here"));
     * }</pre>
     *
     * Result:
     * <pre>{@code login (user: string, password: string): Observable<LoginState>;}</pre>
     *
     * @param method The method
     * @return A new instance of a {@link TsMethodDeclaration}
     */
    public static TsMethodDeclaration fromMethod(TsMethod method){
        return new TsMethodDeclaration(method.getName())
                .addModifiers(method.getModifierList())
                .addParameters(method.getParameterList())
                .addReturnTypes(method.getReturnTypeList());
    }

    /**
     * Extracts the signature of a {@link TsFunction} and creates a {@link TsMethodDeclaration} object
     * which can be used in {@link TsInterface} for example. This function extracts the current
     * state of the given function. Further modification to the given function object will not be
     * affect the created declaration object.<br>
     * The following parts will be used for the declaration object:
     * <ol>
     *     <li>The name of the function</li>
     *     <li>The modifiers</li>
     *     <li>The parameters</li>
     *     <li>The return types</li>
     * </ol>
     *
     * Example:
     * <pre>{@code
     * TsMethodDeclaration methodDeclaration = TsMethodDeclaration.fromFunction(
     *               new TsFunction("login")
     *                       .addStringParameter("user")
     *                       .addStringParameter("password")
     *                       .addObservableReturnType("LoginState")
     *                       .addContent("//Login logic here"));
     * }</pre>
     *
     * Result:
     * <pre>{@code login (user: string, password: string): Observable<LoginState>;}</pre>
     *
     * @param function The function
     * @return A new instance of a {@link TsMethodDeclaration}
     */
    public static TsMethodDeclaration fromFunction(TsFunction function){
        return new TsMethodDeclaration(function.getName())
                .addModifiers(function.getModifierList())
                .addParameters(function.getParameterList())
                .addReturnTypes(function.getReturnTypeList());
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
