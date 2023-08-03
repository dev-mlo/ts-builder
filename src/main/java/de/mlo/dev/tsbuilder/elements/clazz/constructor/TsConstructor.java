package de.mlo.dev.tsbuilder.elements.clazz.constructor;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import de.mlo.dev.tsbuilder.elements.values.Literal;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This class describes how the constructor of a class looks like.
 * <ul>
 *     <li>A class can have only one constructor</li>
 *     <li>The constructor does not have a name</li>
 *     <li>The constructor has 0..n parameters</li>
 *     <li>The constructor has no return type</li>
 * </ul>
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class TsConstructor extends TsElement<TsConstructor> {

    /**
     * The list of all added parameters
     */
    private final TsConstructorParameterList parameterList = new TsConstructorParameterList();

    /**
     * The content of the constructor. Those elements are the code which
     * will be executed.
     */
    private final TsElementList contentList = new TsElementList();

    /**
     * Adds a parameter to the constructor signature
     *
     * @param parameter The parameter to add
     * @return Instance of this {@link TsConstructor}
     */
    public TsConstructor addParameter(TsConstructorParameter parameter) {
        this.parameterList.add(parameter);
        return this;
    }

    /**
     * Adds a parameter of the type 'string' to the constructor signature
     * <pre>
     *     constructor(name: string){}
     * </pre>
     *
     * @param name The name of the parameter
     * @return Instance of this {@link TsConstructor}
     */
    public TsConstructor addStringParameter(String name) {
        return addParameter(TsConstructorParameter.string(name));
    }

    /**
     * Adds an optional parameter of the type 'string' to the constructor signature
     * <pre>
     *     constructor(name?: string){}
     * </pre>
     *
     * @param name The name of the parameter
     * @return Instance of this {@link TsConstructor}
     */
    public TsConstructor addOptionalStringParameter(String name) {
        return addParameter(TsConstructorParameter.optionalString(name));
    }

    /**
     * Adds an array parameter of the type 'string[]' to the constructor signature
     * <pre>
     *     constructor(name: string[]){}
     * </pre>
     *
     * @param name The name of the parameter
     * @return Instance of this {@link TsConstructor}
     */
    public TsConstructor addStringArrayParameter(String name) {
        return addParameter(TsConstructorParameter.stringArray(name));
    }

    public TsConstructor addCustomParameter(String name, String type){
        return addParameter(TsConstructorParameter.custom(name, type));
    }

    /**
     * Adds new code to execute to the constructor content
     * <pre>
     *     constructor(){
     *         console.log('I am the content');
     *     }
     * </pre>
     *
     * @param contentLiteral The content to put into the constructor body
     * @return Instance of this {@link TsConstructor}
     */
    public TsConstructor addContent(String contentLiteral) {
        return addContent(Literal.literal(contentLiteral));
    }

    /**
     * Adds new code to execute to the constructor content
     * <pre>
     *     constructor(){
     *         console.log('I am the content');
     *     }
     * </pre>
     *
     * @param element The content to put into the constructor body
     * @return Instance of this {@link TsConstructor}
     */
    public TsConstructor addContent(TsElement element) {
        this.contentList.add(element);
        return this;
    }

    @Override
    public TsElementWriter<TsConstructor> createWriter(TsContext context) {
        return new TsConstructorWriter(context, this);
    }
}
