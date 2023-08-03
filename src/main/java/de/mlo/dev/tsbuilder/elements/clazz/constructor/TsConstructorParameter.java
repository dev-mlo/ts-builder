package de.mlo.dev.tsbuilder.elements.clazz.constructor;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import de.mlo.dev.tsbuilder.elements.type.ComplexType;
import de.mlo.dev.tsbuilder.elements.type.OrType;
import de.mlo.dev.tsbuilder.elements.type.TsTypes;
import de.mlo.dev.tsbuilder.elements.values.Literal;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

/**
 * This class describes one parameter for a constructor. The parameter must
 * have a name and should declare a type.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
public class TsConstructorParameter extends TsElement<TsConstructorParameter> {

    private final TsModifierList modifierList = new TsModifierList();
    private final String name;

    /**
     * The type of the parameter can be simple type like string and number. But also can
     * be something complex like
     * <pre>
     *     {amount: number, currency: string}
     *     constructor(payment: {amount: number, currency: string}){}
     *
     *      or
     *
     *      payment?: number | Salary | 0
     *      constructor(payment?: number | Salary | 0){}
     * </pre>
     */
    private TsElement<?> type = TsTypes.ANY;

    /**
     * if the parameter is optional
     */
    private boolean optional = false;

    /**
     * Creates a new empty constructor parameter with the given name.
     *
     * @param name The name of the parameter should not be null or empty
     */
    public TsConstructorParameter(String name) {
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Example:
     * <pre>
     *     parameterName: string
     * </pre>
     * <p>
     * The constructor will look like:
     * <pre>
     *    constructor(parameterName: string){
     *    }
     * </pre>
     *
     * @param name The name of the parameter
     * @return A new {@link TsConstructorParameter}
     */
    public static TsConstructorParameter string(String name) {
        return new TsConstructorParameter(name)
                .setType(TsTypes.STRING);
    }

    /**
     * Example:
     * <pre>
     *     parameterName?: string
     * </pre>
     * <p>
     * The constructor will look like:
     * <pre>
     *     constructor(parameterName?: string){
     *     }
     * </pre>
     *
     * @param name The name of the parameter
     * @return A new {@link TsConstructorParameter}
     */
    public static TsConstructorParameter optionalString(String name) {
        return string(name).setOptional();
    }

    /**
     * Example:
     * <pre>
     *     parameterName: string[]
     * </pre>
     * <p>
     * The constructor will look like:
     * <pre>
     *     constructor(parameterName: string[]){
     *     }
     * </pre>
     *
     * @param name The name of the parameter
     * @return A new {@link TsConstructorParameter}
     */
    public static TsConstructorParameter stringArray(String name) {
        return new TsConstructorParameter(name)
                .setType(TsTypes.STRING_ARRAY);
    }

    public static TsConstructorParameter custom(String name, String type) {
        return new TsConstructorParameter(name)
                .setType(type);
    }

    public static TsConstructorParameter complex(String name, ComplexType complexType) {
        return new TsConstructorParameter(name).setComplexType(complexType);
    }

    public static TsConstructorParameter optionalComplex(String name, ComplexType complexType) {
        return complex(name, complexType).setOptional();
    }

    /**
     * Applies a type to the parameter. This can be a simple type like string or number.
     * It is also possible to give the parameter a complex type like
     * <pre>
     *      payment: {amount: number, currency: string}
     *      constructor(payment: {amount: number, currency: string}){}
     *
     *      or
     *
     *      payment?: number | Salary | 0
     *      constructor(payment?: number | Salary | 0){}
     * </pre>
     *
     * @param parameterType Any type for the parameter
     * @return Instance of this {@link TsConstructorParameter}
     */
    public TsConstructorParameter setType(String parameterType) {
        return setType(new Literal(parameterType));
    }

    /**
     * Applies a type to the parameter. This can be a simple type like string or number.
     * It is also possible to give the parameter a complex type like
     * <pre>
     *      payment: {amount: number, currency: string}
     *      constructor(payment: {amount: number, currency: string}){}
     *
     *      or
     *
     *      payment?: number | Salary | 0
     *      constructor(payment?: number | Salary | 0){}
     * </pre>
     *
     * @param type Any type for the parameter
     * @return Instance of this {@link TsConstructorParameter}
     */
    public TsConstructorParameter setType(TsElement<?> type) {
        this.type = type;
        return this;
    }

    public TsConstructorParameter setComplexType(ComplexType complexType) {
        return this.setType(complexType);
    }

    public TsConstructorParameter addOrType(String literal) {
        return addOrType(Literal.literal(literal));
    }

    public TsConstructorParameter addOrType(TsElement<?> type) {
        return setType(new OrType(this.type, type));
    }

    public TsConstructorParameter setNullable() {
        return addOrType(TsTypes.NULL);
    }

    /**
     * Adds the 'private' modifier to the parameter
     *
     * @return Instance of this {@link TsConstructorParameter}
     */
    public TsConstructorParameter setPrivate() {
        this.modifierList.setPrivate();
        return this;
    }

    /**
     * Adds the '?' question mark to the parameter name
     *
     * @return Instance of this {@link TsConstructorParameter}
     */
    public TsConstructorParameter setOptional() {
        return setOptional(true);
    }

    /**
     * Adds the '?' question mark to the parameter name
     *
     * @param optional whether the parameter should be optional or not
     * @return Instance of this {@link TsConstructorParameter}
     */
    public TsConstructorParameter setOptional(boolean optional) {
        this.optional = optional;
        return this;
    }

    @Override
    public TsElementWriter<TsConstructorParameter> createWriter(TsContext context) {
        return new TsConstructorParameterWriter(context, this);
    }
}
