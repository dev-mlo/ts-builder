package de.mlo.dev.tsbuilder.elements.file;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.TsElementContainer;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import de.mlo.dev.tsbuilder.elements.clazz.TsClass;
import de.mlo.dev.tsbuilder.elements.function.TsFunction;
import de.mlo.dev.tsbuilder.elements.interfaces.TsInterface;
import de.mlo.dev.tsbuilder.elements.type.TsType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Arrays;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class TsFile extends TsElementContainer<TsFile> {

    private final TsElementList contentList = new TsElementList();
    private final String name;

    public TsFile(String name) {
        this.name = name;
    }

    public TsFile add(TsElement<?> element){
        this.contentList.add(element);
        return this;
    }

    public TsFile addAll(TsElement<?>... elements){
        this.contentList.addAll(Arrays.asList(elements));
        return this;
    }

    /**
     * Adds a new Type to the file<br>
     * Example:
     * <pre>{@code
     * TsFile file = new TsFile("Authenticator")
     *             .addType(new TsType("User")
     *                     .setExport()
     *                     .setValue(new ComplexType()
     *                             .addStringAttribute("name")
     *                             .addNumberAttribute("age")));
     * }}</pre>
     *
     * Result:
     * <pre>
     * type User = {
     *     name: string,
     *     age: number,
     * }
     * </pre>
     * @param type A type
     * @return The instance of this {@link TsFile} object
     */
    public TsFile addType(TsType type){
        return add(type);
    }

    /**
     * Adds a new interface to the file<br>
     * Example:
     *
     * <pre>{@code
     * TsFile file = new TsFile("MyCalculator")
     *         .addInterface(new TsInterface("Calculator")
     *                 .setExport()
     *                 .addMethod(new TsMethodDeclaration("sum")
     *                         .addNumberParameter("firstNumber")
     *                         .addNumberParameter("secondNumber")
     *                         .addNumberReturnType())
     *                 .addMethod(new TsMethodDeclaration("divide")
     *                         .addNumberParameter("minuend")
     *                         .addNumberParameter("subtrahend")
     *                         .addNumberReturnType()));
     * }</pre>
     *
     * Result:
     * <pre>{@code
     * export interface Calculator {
     *   sum (firstNumber: number, secondNumber: number): number;
     *   divide (minuend: number, subtrahend: number): number;
     * }}</pre>
     * @param tsInterface The interface declaration
     * @return The instance of this {@link TsFile} object
     */
    public TsFile addInterface(TsInterface tsInterface){
        return add(tsInterface);
    }

    public TsFile addClass(TsClass clazz){
        return add(clazz);
    }

    public TsFile addClasses(TsClass... classes){
        return addAll(classes);
    }

    public TsFile addFunction(TsFunction function){
        return add(function);
    }

    public TsFile addFunctions(TsFunction... function){
        return addAll(function);
    }

    @Override
    public TsElementWriter<TsFile> createWriter(TsContext context) {
        return new TsFileWriter(context, this);
    }

    @Override
    public boolean isMergeRequired(TsFile other) {
        return getName().equals(other.getName());
    }
}
