package de.mlo.dev.tsbuilder.elements.clazz;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.*;
import de.mlo.dev.tsbuilder.elements.clazz.constructor.TsConstructor;
import de.mlo.dev.tsbuilder.elements.clazz.field.TsField;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecorator;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecoratorList;
import de.mlo.dev.tsbuilder.elements.function.TsMethod;
import de.mlo.dev.tsbuilder.elements.interfaces.TsInterface;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>
 * [export] class MyClass [extends _superclass_ 0..1] [implements _interface_ 0..n]{
 *
 *     [above_constructor 0..n]
 *     [constructor 0..1]
 *     [below_constructor 0..n]
 * }
 * </pre>
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class TsClass extends TsElementContainer<TsClass> {

    private final TsDecoratorList decoratorList = new TsDecoratorList();
    private final TsModifierList modifierList = new TsModifierList();
    private final TsImplementsList implementsList = new TsImplementsList();
    private final TsElementList contentList = new TsElementList();
    private final String name;
    private String superClassName;

    public TsClass(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public TsClass addContent(String literal) {
        return addContent(TsElement.literal(literal));
    }

    public TsClass addContent(TsElement<?> element) {
        this.contentList.add(element);
        return this;
    }

    public TsClass addContent(TsElement<?>... element) {
        this.contentList.addAll(Arrays.asList(element));
        return this;
    }

    public TsClass addImplements(TsInterface tsInterface) {
        return addImplements(tsInterface.getName());
    }

    public TsClass addImplements(String interfaceName) {
        this.implementsList.add(interfaceName);
        return this;
    }

    public TsClass addDecorator(TsDecorator decorator) {
        this.decoratorList.add(decorator);
        return this;
    }

    public TsClass setSuperClass(String superClassName) {
        this.superClassName = superClassName;
        return this;
    }

    public Optional<TsMethod> getMethod(String methodName) {
        return this.contentList.findMethod(methodName);
    }

    public Optional<TsMethod> getSetterMethod(String methodName) {
        return this.contentList.findSetterMethod(methodName);
    }

    public Optional<TsMethod> getGetterMethod(String methodName) {
        return this.contentList.findGetterMethod(methodName);
    }

    public TsClass addMethod(TsMethod method) {
        return addContent(method);
    }

    /**
     * <p>
     * Appends more content at the end of a method for a given name.
     * </p>
     * <b>Caution:</b> Use specific {@link #addSetterMethodContent(String, TsElement)}
     * and {@link #addGetterMethodContent(String, TsElement)} for this special cases
     * because this methods share the same name.
     *
     * @param methodName The name of the method
     * @param content    The content to add at the end of the method body
     * @return Instance of this {@link TsClass}
     * @throws UnknownElementException if there is no element with the given name
     * @see #addSetterMethodContent(String, TsElement)
     * @see #addGetterMethodContent(String, TsElement) 
     */
    public TsClass addMethodContent(String methodName, TsElement<?> content) {
        TsMethod method = getMethod(methodName)
                .orElseThrow(() -> new UnknownElementException("No method with name %s found".formatted(methodName)));
        method.addContent(content);
        return this;
    }

    /**
     * Appends more content at the end of a setter method for a given name.
     *
     * @param methodName The name of the method
     * @param content    The content to add at the end of the method body
     * @return Instance of this {@link TsClass}
     * @throws UnknownElementException if there is no element with the given name
     * @see #addGetterMethodContent(String, TsElement)
     * @see #addMethodContent(String, TsElement)
     */
    public TsClass addSetterMethodContent(String methodName, TsElement<?> content) {
        TsMethod method = getSetterMethod(methodName)
                .orElseThrow(() -> new UnknownElementException("No setter method with name %s found".formatted(methodName)));
        method.addContent(content);
        return this;
    }

    /**
     * Appends more content at the end of a getter method for a given name.
     *
     * @param methodName The name of the method
     * @param content    The content to add at the end of the method body
     * @return Instance of this {@link TsClass}
     * @throws UnknownElementException if there is no element with the given name
     * @see #addSetterMethodContent(String, TsElement)
     * @see #addMethodContent(String, TsElement)
     */
    public TsClass addGetterMethodContent(String methodName, TsElement<?> content) {
        TsMethod method = getGetterMethod(methodName)
                .orElseThrow(() -> new UnknownElementException("No getter method with name %s found".formatted(methodName)));
        method.addContent(content);
        return this;
    }

    public TsClass setExport() {
        this.modifierList.setExport();
        return this;
    }

    @Override
    public TsElementWriter<TsClass> createWriter(TsContext context) {
        return new TsClassWriter(context, this);
    }

    public TsClass setConstructor(TsConstructor constructor) {
        return addContent(constructor);
    }

    public TsClass addField(String name, TsElement<?> type, TsElement<?> value) {
        return addField(new TsField(name).setType(type).setValue(value));
    }

    public TsClass addStringField(String name, String value) {
        return addField(TsField.string(name, value));
    }

    public TsClass addStringArrayField(String name, String... values) {
        return addField(TsField.stringArray(name, values));
    }

    public TsClass addField(TsField field) {
        return addContent(field);
    }

    public TsClass addFields(TsField... field) {
        return addContent(field);
    }

    public TsClass addBlankLine(){
        return addContent(TsElement.literal(""));
    }

    @Override
    public boolean isMergeRequired(TsClass other) {
        return getName().equals(other.getName());
    }
}
