package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.*;
import de.mlo.dev.tsbuilder.elements.common.TsModifier;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecorator;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecoratorList;
import de.mlo.dev.tsbuilder.elements.interfaces.TsMethodDeclaration;
import de.mlo.dev.tsbuilder.elements.type.ComplexType;
import de.mlo.dev.tsbuilder.elements.values.Literal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.*;

/**
 * Methods are functions inside a class
 *
 * <pre>{@code
 * foo (bar: string, baz?: number): Date {
 *     console.log(`${bar} and ${baz}`);
 *     return new Date();
 * }
 * }</pre>
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class TsMethod extends TsElementContainer<TsMethod> {

    private final TsDecoratorList decoratorList = new TsDecoratorList();
    private final TsModifierList modifierList = new TsModifierList();
    private final TsFunctionParameterList parameterList = new TsFunctionParameterList();
    private final TsFunctionReturnTypeList returnTypeList = new TsFunctionReturnTypeList();
    private final TsElementList contentList = new TsElementList();
    private final String name;

    /**
     * Creates a new Method with a given name. After the creation you can
     * <ol>
     *     <li>add decorators</li>
     *     <li>add modifiers (like 'public' or 'private'</li>
     *     <li>add parameters</li>
     *     <li>add return types</li>
     *     <li>add content</li>
     * </ol>
     * <b>Hint:</b> In typescript the name of the method must be unique in the
     * class no matter if the parameters differs.
     *
     * @param name The name of the method.
     */
    public TsMethod(String name) {
        this.name = Objects.requireNonNull(name);
    }

    /**
     * Creates the {@link TsMethod} from a Declaration. After the creation
     * you can still modify the method, and you can add you content.
     *
     * @param declaration The methode declaration / signature
     * @return A new Instance of a {@link TsMethod}
     */
    public static TsMethod fromDeclaration(TsMethodDeclaration declaration) {
        TsMethod tsMethod = new TsMethod(declaration.getName())
                .addModifiers(declaration.getModifierList())
                .addParameters(declaration.getParameterList())
                .addReturnTypes(declaration.getReturnTypeList());
        tsMethod.addImports(declaration.getImportList());
        return tsMethod;
    }

    /**
     * Determines an added decorator by its name.
     *
     * @param name The name of the decorator (usually starts with '@')
     * @return A decorator
     */
    public Optional<TsDecorator> getDecorator(String name){
        return decoratorList.get(name);
    }

    /**
     * Determines an added decorator by its name.
     *
     * @param name The name of the decorator (usually starts with '@')
     * @return A decorator
     * @throws UnknownElementException if there is no decorator with the given name
     */
    public TsDecorator getDecoratorOrThrow(String name){
        return getDecorator(name)
                .orElseThrow(() -> new UnknownElementException("No decorator with name '%s' present".formatted(name)));
    }

    /**
     * Adds a new decorator on top of the method.
     * <pre>{@code
     * @Input()
     * set value(value: string){
     *     this._value = value;
     * }
     * }</pre>
     *
     * @param decorator A new decorator
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addDecorator(TsDecorator decorator) {
        this.decoratorList.add(decorator);
        return this;
    }

    /**
     * Adds a new parameter to the method signature.<br>
     * You can also use one of the predefined function for common types:
     * <ul>
     *     <li>{@link #addStringParameter(String)}</li>
     *     <li>{@link #addNumberParameter(String)}</li>
     *     <li>{@link #addDateParameter(String)}</li>
     *     <li>{@link #addComplexParameter(String, ComplexType)}</li>
     *     <li>{@link #addCustomParameter(String, String)}</li>
     * </ul>
     * There are also equivalents for adding optional parameters and arrays.
     *
     * @param parameter A new parameter
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addParameter(TsFunctionParameter parameter) {
        this.parameterList.add(parameter);
        return this;
    }

    public TsMethod addParameters(TsFunctionParameterList parameters) {
        this.parameterList.addAll(parameters);
        return this;
    }

    public TsMethod addStringParameter(String name) {
        return addParameter(TsFunctionParameter.string(name));
    }

    public TsMethod addOptionalStringParameter(String name) {
        return addParameter(TsFunctionParameter.optionalString(name));
    }

    public TsMethod addStringArrayParameter(String name) {
        return addParameter(TsFunctionParameter.stringArray(name));
    }

    public TsMethod addOptionalStringArrayParameter(String name) {
        return addParameter(TsFunctionParameter.optionalStringArray(name));
    }

    public TsMethod addNumberParameter(String name) {
        return addParameter(TsFunctionParameter.number(name));
    }

    public TsMethod addOptionalNumberParameter(String name) {
        return addParameter(TsFunctionParameter.optionalNumber(name));
    }

    public TsMethod addNumberArrayParameter(String name) {
        return addParameter(TsFunctionParameter.numberArray(name));
    }

    public TsMethod addOptionalNumberArrayParameter(String name) {
        return addParameter(TsFunctionParameter.optionalNumberArray(name));
    }

    public TsMethod addDateParameter(String name) {
        return addParameter(TsFunctionParameter.date(name));
    }

    public TsMethod addOptionalDateParameter(String name) {
        return addParameter(TsFunctionParameter.optionalDate(name));
    }

    public TsMethod addDateArrayParameter(String name) {
        return addParameter(TsFunctionParameter.dateArray(name));
    }

    public TsMethod addOptionalDateArrayParameter(String name) {
        return addParameter(TsFunctionParameter.optionalDateArray(name));
    }

    public TsMethod addComplexParameter(String name, ComplexType complexType) {
        return addParameter(TsFunctionParameter.complex(name, complexType));
    }

    public TsMethod addOptionalComplexParameter(String name, ComplexType complexType) {
        return addParameter(TsFunctionParameter.optionalComplex(name, complexType));
    }

    public TsMethod addCustomParameter(String name, String type) {
        return addParameter(TsFunctionParameter.custom(name, type));
    }

    public TsMethod addOptionalCustomParameter(String name, String type) {
        return addParameter(TsFunctionParameter.optionalCustom(name, type));
    }

    public TsMethod addCustomArrayParameter(String name, String type) {
        return addParameter(TsFunctionParameter.customArray(name, type));
    }

    public TsMethod addOptionalCustomArrayParameter(String name, String type) {
        return addParameter(TsFunctionParameter.optionalCustomArray(name, type));
    }

    /**
     * Adds new content at the end of the method body.
     *
     * @param rawContent The raw content
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addContent(String rawContent) {
        return addContent(TsElement.literal(rawContent));
    }

    /**
     * Adds new content at the end of the method body.
     *
     * @param element The content to add
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addContent(TsElement element) {
        this.contentList.add(element);
        return this;
    }

    public TsMethod addComment(String comment){
        return addContent(TsElement.literal("// " + comment));
    }

    /**
     * Marks the method as <u>public</u> so code outside a class can access it
     * <pre>{@code
     * public foo (): string {
     *     return 'bar'
     * }
     * }</pre>
     *
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod setPublic() {
        modifierList.setPublic();
        return this;
    }

    /**
     * Marks the method as <u>private</u> so code outside a class cannot access it
     * <pre>{@code
     * private foo (): string {
     *     return 'bar'
     * }
     * }</pre>
     *
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod setPrivate() {
        modifierList.setPrivate();
        return this;
    }

    /**
     * Adds a new custom modifier. Modifiers will be added before the method name.
     * The order of the added modifiers will be maintained.<br>
     * There are several predefined functions to add a modifier:
     * <ul>
     *     <li>{@link #setPrivate()}</li>
     *     <li>{@link #setPublic()}</li>
     * </ul>
     *
     * @param modifier A new modifier
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addModifiers(TsModifier modifier) {
        modifierList.add(modifier);
        return this;
    }

    /**
     * Adds a new custom list of modifiers. Modifiers will be added before the method name.
     * The order of the added modifiers will be maintained.<br>
     * There are several predefined functions to add a modifier:
     * <ul>
     *     <li>{@link #setPrivate()}</li>
     *     <li>{@link #setPublic()}</li>
     * </ul>
     *
     * @param literals A list of modifiers to be added
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addModifiers(String... literals) {
        List<TsModifier> modifierList = Arrays.stream(literals)
                .map(TsModifier::new)
                .toList();
        return addModifiers(modifierList);
    }

    /**
     * Adds a new custom list of modifiers. Modifiers will be added before the method name.
     * The order of the added modifiers will be maintained.<br>
     * There are several predefined functions to add a modifier:
     * <ul>
     *     <li>{@link #setPrivate()}</li>
     *     <li>{@link #setPublic()}</li>
     * </ul>
     *
     * @param modifiers A list of modifiers to be added
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addModifiers(Collection<TsModifier> modifiers) {
        modifierList.addAll(modifiers);
        return this;
    }

    /**
     * Adds a new return type to this method. Multiple types will be
     * <u>OR</u> connected
     * <pre>{@code
     * foo (): bar | baz {
     *     // content
     * }
     * }</pre>
     *
     * @param returnType A new return type
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addReturnType(TsElement returnType) {
        this.returnTypeList.add(returnType);
        return this;
    }

    /**
     * Adds a new return type to this method. Multiple types will be
     * <u>OR</u> connected
     * <pre>{@code
     * foo (): bar | baz {
     *     // content
     * }
     * }</pre>
     *
     * @param returnType A new return type
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addReturnType(TsFunctionReturnType returnType) {
        return addReturnType((TsElement) returnType);
    }

    /**
     * Adds new return types to this method. Multiple types will be
     * <u>OR</u> connected
     * <pre>{@code
     * foo (): bar | baz {
     *     // content
     * }
     * }</pre>
     *
     * @param literals New return types
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addReturnTypes(String... literals){
        List<Literal> types = Arrays.stream(literals)
                .map(TsElement::literal)
                .toList();
        return addReturnTypes(types);
    }

    /**
     * Adds new return types to this method. Multiple types will be
     * <u>OR</u> connected
     * <pre>{@code
     * foo (): bar | baz {
     *     // content
     * }
     * }</pre>
     *
     * @param returnTypes New return types
     * @return Instance of this {@link TsMethod}
     */
    public TsMethod addReturnTypes(Collection<? extends TsElement<?>> returnTypes) {
        getReturnTypeList().addAll(returnTypes);
        return this;
    }

    public TsMethod setOptional(boolean optional){
        getReturnTypeList().setOptional(optional);
        return this;
    }

    /**
     * Crates a new instance of {@link TsMethodDeclaration} from the
     * current information of this instance of {@link TsMethod}.<br>
     * The Declaration consists of the
     * <ol>
     *     <li>{@link #getModifierList() Modifiers}</li>
     *     <li>{@link #getName() Name}</li>
     *     <li>{@link #getParameterList() Parameters}</li>
     *     <li>{@link #getReturnTypeList() Return types}</li>
     * </ol>
     * The declaration does <u>not</u> contain the
     * <ul>
     *     <li>{@link #getContentList() Content}</li>
     *     <li>{@link #getDecoratorList() Decorators}</li>
     * </ul>
     *
     * @return A new Instance of a {@link TsMethodDeclaration}
     */
    public TsMethodDeclaration getMethodDeclaration() {
        return TsMethodDeclaration.fromMethod(this);
    }

    /**
     * @return If this method is a setter like:
     * <pre>{@code
     * set value(value: string){
     *     this._value = value;
     * }
     * }</pre>
     */
    public boolean isSetter() {
        return modifierList.isSetter();
    }

    /**
     * @return If this method is a getter like:
     * <pre>{@code
     * get value(value: string){
     *     this._value = value;
     * }
     * }</pre>
     */
    public boolean isGetter() {
        return modifierList.isGetter();
    }

    @Override
    public TsElementWriter<TsMethod> createWriter(TsContext context) {
        return new TsMethodWriter(context, this);
    }

    /**
     * In TypeScript it is not allowed to have multiple functions with the same name
     *
     * @param other The other function to check
     * @return true if this and the other function must be merged
     */
    public boolean isMergeRequired(TsMethod other){
        return getName().equals(other.getName())
                && isGetter() == other.isGetter()
                && isSetter() == other.isSetter();
    }

    @Override
    public TsMethod merge(TsMethod other) {
        TsMethodDeclaration thisDeclaration = getMethodDeclaration();
        TsMethodDeclaration otherDeclaration = other.getMethodDeclaration();
        if(thisDeclaration.equals(otherDeclaration)){
            return super.merge(other);
        }
        throw MergeException.signatureMismatch(thisDeclaration, otherDeclaration);
    }
}
