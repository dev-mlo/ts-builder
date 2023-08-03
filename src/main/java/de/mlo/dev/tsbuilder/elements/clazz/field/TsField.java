package de.mlo.dev.tsbuilder.elements.clazz.field;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.MergeException;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsDeclarativeElement;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.common.TsModifier;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecorator;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecoratorList;
import de.mlo.dev.tsbuilder.elements.function.TsFunctionParameter;
import de.mlo.dev.tsbuilder.elements.function.TsMethod;
import de.mlo.dev.tsbuilder.elements.type.ComplexType;
import de.mlo.dev.tsbuilder.elements.type.TsSimpleType;
import de.mlo.dev.tsbuilder.elements.type.TsTypes;
import de.mlo.dev.tsbuilder.elements.values.ArrayValue;
import de.mlo.dev.tsbuilder.elements.values.Literal;
import de.mlo.dev.tsbuilder.elements.values.NumberValue;
import de.mlo.dev.tsbuilder.elements.values.StringValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class TsField extends TsDeclarativeElement<TsField> {
    private final TsDecoratorList decoratorList = new TsDecoratorList();
    private final String name;
    private String namePrefix = "";
    private boolean isOptional = false;
    private boolean isNeverNull = false;
    private TsElement<?> type;
    private TsElement<?> value;
    private TsMethod setter;
    private TsMethod getter;

    public TsField(String name) {
        this.name = name;
    }

    public TsField(TsField field){
        super(field);
        this.name = field.getName();
        this.decoratorList.addAll(field.getDecoratorList());
        this.namePrefix = field.namePrefix;
        this.isOptional = field.isOptional;
        this.isNeverNull = field.isNeverNull;
        this.type = field.type;
        this.value = field.value;
        this.setter = field.setter;
        this.getter = field.getter;
    }

    /**
     * Creates a string typed field without a value:
     * <pre>
     *     myField: string;
     * </pre>
     * If you do not initialize the field within the constructor, you should mark
     * this field as {@link #setOptional() optional} or
     * {@link #setValue(TsElement) assign a value}. If the field is optional
     * you can use the {@link #optionalString(String)} factory function.
     *
     * @param name The name of the field.
     * @return A new {@link TsField} instance
     */
    public static TsField string(String name) {
        return new TsField(name).setType(TsTypes.STRING);
    }

    public static TsField string(String name, String value) {
        return string(name).setStringValue(value);
    }

    /**
     * Creates an optional string typed field
     * <pre>
     *     myField?: string;
     * </pre>
     *
     * @param name The name of the field
     * @return A new {@link TsField} instance
     */
    public static TsField optionalString(String name) {
        return new TsField(name).setType(TsTypes.STRING).setOptional();
    }

    public static TsField optionalString(String name, String value) {
        return optionalString(name).setStringValue(value).setOptional();
    }

    public static TsField stringArray(String name) {
        return new TsField(name).setType(TsTypes.STRING_ARRAY);
    }

    public static TsField stringArray(String name, String... values) {
        return stringArray(name).setStringArrayValue(values);
    }

    public static TsField emptyStringArray(String name) {
        return stringArray(name, new String[0]);
    }

    public static TsField number(String name) {
        return new TsField(name).setType(TsTypes.NUMBER);
    }

    public static TsField number(String name, String value) {
        return number(name).setNumberValue(value);
    }

    public static TsField number(String name, Number value) {
        return number(name).setNumberValue(value);
    }

    public static TsField optionalNumber(String name) {
        return number(name).setOptional();
    }

    public static TsField optionalNumber(String name, String value) {
        return optionalNumber(name).setNumberValue(value);
    }

    public static TsField optionalNumber(String name, Number value) {
        return optionalNumber(name).setNumberValue(value);
    }

    public static TsField numberArray(String name) {
        return new TsField(name).setType(TsTypes.NUMBER_ARRAY);
    }

    public static TsField numberArray(String name, String... values) {
        return numberArray(name).setNumberArrayValues(values);
    }

    public static TsField numberArray(String name, Number... values) {
        return numberArray(name).setNumberArrayValues(values);
    }

    public static TsField emptyNumberArray(String name) {
        return numberArray(name).setNumberArrayValues(new String[0]);
    }

    public static TsField custom(String name, String type) {
        return custom(name, new TsSimpleType(type));
    }

    public static TsField custom(String name, ComplexType type) {
        return custom(name, type);
    }

    public static TsField custom(String name, TsElement<?> type) {
        return new TsField(name).setType(type);
    }

    public static TsField optionalCustom(String name, String type){
        return custom(name, type).setOptional();
    }

    public static TsField optionalCustom(String name, ComplexType type){
        return custom(name, type).setOptional();
    }

    public static TsField optionalCustom(String name, TsElement<?> type){
        return custom(name, type).setOptional();
    }

    public static TsField array(String name, TsElement<?> type, ArrayValue arrayValue) {
        return new TsField(name).setType(type).setValue(arrayValue);
    }

    public static TsField stringArray(String name, ArrayValue arrayValue) {
        return array(name, TsTypes.STRING_ARRAY, arrayValue);
    }

    public TsField setType(TsElement<?> type) {
        this.type = type;
        return this;
    }

    /**
     * Applies a string value to the field. If the type was unset, the type becomes 'string'.
     *
     * @param value A string value. Will be wrapped in single quotes
     * @return Instance of this {@link TsField}
     */
    public TsField setStringValue(String value) {
        if (type == null) {
            setType(TsTypes.STRING);
        }
        return setValue(new StringValue(value));
    }

    /**
     * Applies a string array value to the field. If the type was unset, the type becomes 'string[]'.
     *
     * @param values A string array value. All values will be wrapped in single quotes
     * @return Instance of this {@link TsField}
     */
    public TsField setStringArrayValue(String... values) {
        if (type == null) {
            setType(TsTypes.STRING_ARRAY);
        }
        return setValue(new ArrayValue().addStrings(values));
    }

    /**
     * Applies a number value to the field. If the type was unset, the type becomes 'number'.
     *
     * @param value A number value
     * @return Instance of this {@link TsField}
     */
    public TsField setNumberValue(String value) {
        if (type == null) {
            setType(TsTypes.NUMBER);
        }
        return setValue(new NumberValue(value));
    }

    /**
     * Applies a number value to the field. If the type was unset, the type becomes 'number'.
     *
     * @param value A number value
     * @return Instance of this {@link TsField}
     */
    public TsField setNumberValue(Number value) {
        if (type == null) {
            setType(TsTypes.NUMBER);
        }
        return setValue(new NumberValue(value));
    }

    /**
     * Applies a number array value to the field. If the type was unset, the type becomes 'number[]'.
     *
     * @param values A number array value
     * @return Instance of this {@link TsField}
     */
    public TsField setNumberArrayValues(String... values) {
        if (type == null) {
            setType(TsTypes.NUMBER_ARRAY);
        }
        return setValue(new ArrayValue().addNumbers(values));
    }

    /**
     * Applies a number array value to the field. If the type was unset, the type becomes 'number[]'.
     *
     * @param values A number array value
     * @return Instance of this {@link TsField}
     */
    public TsField setNumberArrayValues(Number... values) {
        if (type == null) {
            setType(TsTypes.NUMBER_ARRAY);
        }
        return setValue(new ArrayValue().addNumbers(values));
    }

    /**
     * Applies a custom value to this field.
     *
     * @param value Any value you would like to apply. The value can be as complex as you wish.
     * @return Instance of this {@link TsField}
     */
    public TsField setValue(TsElement<?> value) {
        this.value = value;
        return this;
    }

    /**
     * Marks the field as optional.
     * <pre>{@code
     * foo?: string;
     * bar?: string = 'can be optional (undefined)'
     * }</pre>
     *
     * @return Instance of this {@link TsField}
     */
    public TsField setOptional() {
        return setOptional(true);
    }

    /**
     * Can mark the field as optional.
     * <pre>{@code
     * foo?: string;
     * bar?: string = 'can be optional (undefined)'
     * }</pre>
     *
     * @param optional Whether the field should be optional (can be 'undefined')
     * @return Instance of this {@link TsField}
     */
    public TsField setOptional(boolean optional) {
        isOptional = optional;
        return this;
    }

    /**
     * Marks the field as never be null or undefined
     * <pre>{@code
     * foo!: string; // <-- maybe set from outside the class or via a framework
     * }</pre>
     *
     * @return Instance of this {@link TsField}
     */
    public TsField setNeverNull() {
        return setNeverNull(true);
    }

    /**
     * Can mark the field as never be null or undefined
     * <pre>{@code
     * foo!: string; // <-- maybe set from outside the class or via a framework
     * }</pre>
     *
     * @param neverNull Whether the field is never null or not
     * @return Instance of this {@link TsField}
     */
    public TsField setNeverNull(boolean neverNull) {
        this.isNeverNull = neverNull;
        return this;
    }

    public TsField addDecorator(TsDecorator decorator) {
        this.decoratorList.add(decorator);
        return this;
    }

    /**
     * <p>
     * This function adds a prefix to the original name of the field. This might come in handy if
     * you have to modify the name of a field because of reasons. One use case might be the
     * creation of getter and setter. The name of the getter or setter must be different to the
     * name of the field. In this case you can use this function to add a prefix to the fields
     * name.
     * </p>
     * Example with setter and getter and the prefix is "_"
     * <pre>{@code
     * _value: string;
     * set value(value: string){
     *     this._value = value;
     * }
     * get value(): string{
     *     return this._value;
     * }
     * }</pre>
     *
     * @param namePrefix The prefix of the name
     * @return Instance of this {@link TsField}
     */
    public TsField setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    /**
     * Adds a default setter function after the field declaration with the given decorator.
     * <pre>{@code
     * _value: string?
     * @Input() set value(value?: string){
     *     this._value = value;
     * }
     * }</pre>
     * You can modify the created setter method by using {@link #getSetter()}.<br>
     * <b>Caution:</b> The name of the field will be prefixed with "_".
     * If the name of the field was "value" it becomes "_value".
     *
     * @return Instance of this {@link TsField}
     */
    public TsField addSetter(TsDecorator decorator) {
        TsMethod setter = createSetter().addDecorator(decorator);
        return setNamePrefix("_")
                .setSetter(setter);
    }

    /**
     * Adds a default setter function after the field declaration.
     * <pre>{@code
     * _value: string?
     * set value(value?: string){
     *     this._value = value;
     * }
     * }</pre>
     * You can modify the created setter method by using {@link #getSetter()}.<br>
     * <b>Caution:</b> The name of the field will be prefixed with "_".
     * If the name of the field was "value" it becomes "_value".
     *
     * @return Instance of this {@link TsField}
     */
    public TsField addSetter() {
        return setNamePrefix("_")
                .setSetter(createSetter());
    }

    private TsMethod createSetter() {
        TsFunctionParameter parameter = new TsFunctionParameter("value")
                .setType(getType())
                .setOptional(isOptional());
        return new TsMethod(getName())
                .addModifiers(new TsModifier("set"))
                .addParameter(parameter)
                .addContent(new Literal("this._" + getName() + " = value;"));
    }

    /**
     * Applies a custom setter method. The setter method will be added after the
     * field declaration.<br>
     * <b>Hint:</b> The name of the field will <u>not</u> be prefixed with "_".
     * You have to use {@link #setNamePrefix(String)} to apply a custom prefix
     * for the field if the name with the given method clashes.
     *
     * @param setter A setter function for this field
     * @return Instance of this {@link TsField}
     * @see #addSetter()
     */
    public TsField setSetter(TsMethod setter) {
        this.setter = setter;
        addAfterElementContent(setter);
        return this;
    }

    /**
     * Adds a default getter function after the field declaration.
     * <pre>{@code
     * _value: string?
     * get value(): string | undefined{
     *     return this._value;
     * }
     * }</pre>
     * You can modify the created setter method by using {@link #getSetter()}.<br>
     * <b>Caution:</b> The name of the field will be prefixed with "_".
     * If the name of the field was "value" it becomes "_value".
     *
     * @return Instance of this {@link TsField}
     */
    public TsField addGetter() {
        this.setNamePrefix("_");
        return setGetter(new TsMethod(getName())
                .addModifiers(new TsModifier("get"))
                .addReturnType(getType())
                .setOptional(isOptional())
                .addContent(new Literal("return this._" + getName() + ";")));
    }

    /**
     * Applies a custom getter method. The getter method will be added after the
     * field declaration.<br>
     * <b>Hint:</b> The name of the field will <u>not</u> be prefixed with "_".
     * You have to use {@link #setNamePrefix(String)} to apply a custom prefix
     * for the field if the name with the given method clashes.<br>
     * <b>Hint:</b> If you would like to add default getter function you can
     * use the {@link #addGetter()} function.
     *
     * @param getter A getter function for this field
     * @return Instance of this {@link TsField}
     * @see #addGetter()
     */
    public TsField setGetter(TsMethod getter) {
        this.getter = getter;
        addAfterElementContent(getter);
        return this;
    }

    @Override
    public TsElementWriter<TsField> createWriter(TsContext context) {
        return new TsFieldWriter(context, this);
    }

    @Override
    public boolean isMergeRequired(TsField other) {
        return this.name.equals(other.name);
    }

    public TsField merge(TsField other){
        if(this.equals(other)){
            return this;
        }
        throw MergeException.signatureMismatch(this, other);
    }
}
