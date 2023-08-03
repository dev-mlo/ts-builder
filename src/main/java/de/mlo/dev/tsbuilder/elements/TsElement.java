package de.mlo.dev.tsbuilder.elements;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.imports.TsImport;
import de.mlo.dev.tsbuilder.elements.imports.TsImportList;
import de.mlo.dev.tsbuilder.elements.values.Literal;
import lombok.Getter;

import java.util.Collection;

/**
 * The godfather of any element in the typescript universe.<br>
 * The definition of a typescript element is:
 * <ol>
 *     <li>Can build itself</li>
 *     <li>Can build children</li>
 *     <li>Can provides imports</li>
 *     <li>Can print imports</li>
 * </ol>
 */
@Getter
@SuppressWarnings("unchecked")
public abstract class TsElement<E extends TsElement<E>> {

    private final TsElementList beforeElementContent = new TsElementList();
    private final TsElementList afterElementContent = new TsElementList();
    private final TsImportList importList = new TsImportList();

    public TsElement() {

    }

    /**
     * Copy constructor
     *
     * @param element The element to copy from
     */
    public TsElement(E element) {
        this.beforeElementContent.addAll(element.getBeforeElementContent());
        this.afterElementContent.addAll(element.getAfterElementContent());
        this.importList.addAll(element.getImportList());
    }

    /**
     * Convenient function to create a {@link TsElement} from a string literal.
     * The element will be printed as given.
     *
     * @param rawContent The raw element
     * @return A new instance of a {@link TsElement}
     */
    public static Literal literal(String rawContent) {
        return new Literal(rawContent);
    }

    protected abstract TsElementWriter<E> createWriter(TsContext context);

    /**
     * <p>
     * This will build the declared TypeScript elements.
     * </p>
     * <b>Important:</b> This function will <u>not</u> print the imports. If would
     * like to have a complete file, you should use {@link #buildWithImports()}.
     *
     * @return Your defined TypeScript elements as a string.
     */
    public String build() {
        return build(new TsContext());
    }

    /**
     * <p>
     * This will build the declared TypeScript elements.
     * </p>
     * <b>Important:</b> This function will <u>not</u> print the imports. If would
     * like to have a complete file, you should use {@link #buildWithImports()}.
     *
     * @param context The context to build with. The context can control how to
     *                generate the TypeScript elements.
     * @return Your defined TypeScript elements as a string.
     */
    public String build(TsContext context) {
        return createBeforeAndAfterElementsWriter(createWriter(context)).build();
    }

    /**
     * <p>
     * This will build the declared TypeScript elements and includes all defined imports.
     * </p>
     *
     * @return Your defined TypeScript elements as a string.
     */
    public String buildWithImports() {
        return buildWithImports(new TsContext());
    }

    /**
     * <p>
     * This will build the declared TypeScript elements and includes all defined imports.
     * </p>
     *
     * @param context The context to build with. The context can control how to
     *                generate the TypeScript elements.
     * @return Your defined TypeScript elements as a string.
     */
    public String buildWithImports(TsContext context) {
        TsElementWriter<E> wrappedWriter = createBeforeAndAfterElementsWriter(createWriter(context));
        return createImportWriter(context, wrappedWriter).build().strip();
    }

    protected TsElementWriter<TsElement<?>> createImportWriter(TsContext context, TsElementWriter<?> contentWriter){
        return new WithImportWriter(context, this, contentWriter);
    }

    /**
     * Wraps a {@link TsElementWriter} in a new {@link TsElementWriter} which adds the
     * declared elements to print before and after 'this' element.
     *
     * @param delegate The {@link TsElementWriter} of the {@link TsElement} implementation
     * @param <E>      The implementation type of the {@link TsElement}
     * @return The {@link TsElementWriter} which will add the output for elements
     * before and after 'this' element
     */
    private static <E extends TsElement<E>> TsElementWriter<E> createBeforeAndAfterElementsWriter(TsElementWriter<E> delegate) {
        return new TsElementWriter<>(delegate.getContext(), delegate.getElement()) {
            @Override
            public String build() {
                String beforeElementContent = buildBeforeElementContent();
                String content = delegate.build();
                String afterElementContent = buildAfterElementContent();
                return beforeElementContent + content + afterElementContent;
            }
        };
    }

    /**
     * Adds a new import.<br>
     * Rules:
     * <ul>
     *     <li>Any element can define its own imports</li>
     *     <li>
     *         Imports are not printed unless the {@link #buildWithImports()} function is called.<br>
     *         The {@link #build()} function will skip the imports.
     *     </li>
     *     <li>Imports will be merged together -> Duplicated defined imports are not an issue</li>
     * </ul>
     *
     * <pre>{@code
     * import {ModuleName} from './path/to/module';
     * }</pre>
     *
     * @param module   The name of the module
     * @param fromPath The path to the file or node module
     * @return The created {@link TsImport} object. You can add more modules by using
     * {@link TsImport#addModuleName(String)}
     */
    public TsImport addImport(String module, String fromPath) {
        TsImport tsImport = new TsImport(module, fromPath);
        addImport(tsImport);
        return tsImport;
    }

    /**
     * Adds a new import.<br>
     * Rules:
     * <ul>
     *     <li>Any element can define its own imports</li>
     *     <li>
     *         Imports are not printed unless the {@link #buildWithImports()} function is called.<br>
     *         The {@link #build()} function will skip the imports.
     *     </li>
     *     <li>Imports will be merged together -> Duplicated defined imports are not an issue</li>
     * </ul>
     *
     * <pre>{@code
     * import {ModuleName} from './path/to/module';
     * }</pre>
     *
     * @param tsImport The import with module and path
     * @return Instance of this {@link TsElement}
     */
    public E addImport(TsImport tsImport) {
        importList.add(tsImport);
        return (E) this;
    }

    /**
     * Adds a new imports.<br>
     * Rules:
     * <ul>
     *     <li>Any element can define its own imports</li>
     *     <li>
     *         Imports are not printed unless the {@link #buildWithImports()} function is called.<br>
     *         The {@link #build()} function will skip the imports.
     *     </li>
     *     <li>Imports will be merged together -> Duplicated defined imports are not an issue</li>
     * </ul>
     *
     * <pre>{@code
     * import {ModuleName} from './path/to/module';
     * }</pre>
     *
     * @param imports The imports with module and path
     * @return Instance of this {@link TsElement}
     */
    public E addImports(Collection<TsImport> imports) {
        importList.addAll(imports);
        return (E) this;
    }

    /**
     * This will add any element before 'this' element. It allows you to group
     * some semantic functionality in one block.
     *
     * @param beforeElementContent Any element to add before 'this' element.
     * @return Instance of this {@link TsElement}
     */
    public E addBeforeElementContent(TsElement<?> beforeElementContent) {
        this.beforeElementContent.add(beforeElementContent);
        return (E) this;
    }

    /**
     * This will add any elements before 'this' element. It allows you to group
     * some semantic functionality in one block.
     *
     * @param beforeElementContentList Any elements to add before 'this' element.
     * @return Instance of this {@link TsElement}
     */
    public E addBeforeElementContent(Collection<TsElement<?>> beforeElementContentList) {
        this.beforeElementContent.addAll(beforeElementContentList);
        return (E) this;
    }

    /**
     * This will add any element after 'this' element. It allows you to group
     * some semantic functionality in one block.
     *
     * @param afterElementContent Any element to add after 'this' element.
     * @return Instance of this {@link TsElement}
     */
    public E addAfterElementContent(TsElement<?> afterElementContent) {
        this.afterElementContent.add(afterElementContent);
        return (E) this;
    }

    /**
     * This will add any elements after 'this' element. It allows you to group
     * some semantic functionality in one block.
     *
     * @param afterElementContentList Any elements to add after 'this' element.
     * @return Instance of this {@link TsElement}
     */
    public E addAfterElementContent(Collection<TsElement<?>> afterElementContentList) {
        this.afterElementContent.addAll(afterElementContentList);
        return (E) this;
    }
}
