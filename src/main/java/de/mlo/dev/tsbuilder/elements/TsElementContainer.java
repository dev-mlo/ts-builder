package de.mlo.dev.tsbuilder.elements;

import de.mlo.dev.tsbuilder.elements.clazz.TsClass;
import de.mlo.dev.tsbuilder.elements.file.TsFile;
import de.mlo.dev.tsbuilder.elements.function.TsFunction;
import de.mlo.dev.tsbuilder.elements.function.TsMethod;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * A container is a TypeScript element which can have (complex) sub elements. Usually a
 * container is one of these:
 * <ul>
 *     <li>{@link TsFile File}</li>
 *     <li>{@link TsClass Class}</li>
 *     <li>{@link TsFunction Function} / {@link TsMethod Method}</li>
 * </ul>
 *
 * @param <T> The exact type of the container
 */
@SuppressWarnings("unchecked")
public abstract class TsElementContainer<T extends TsElementContainer<T>> extends TsDeclarativeElement<T> {

    /**
     * The default implementation will join both given lists so the result will
     * contain the content of both containers which are merged.
     */
    private static final BiConsumer<TsElementList, TsElementList> DEFAULT_CONTENT_RESOLVER =
            (TsElementList list, TsElementList c) -> {
                if (!list.equals(c)) {
                    list.addAll(c);
                }
            };

    private BiConsumer<TsElementList, TsElementList> contentResolver = DEFAULT_CONTENT_RESOLVER;

    public TsElementContainer() {
    }

    public TsElementContainer(T element) {
        super(element);
    }

    /**
     * Has to deliver the <b>content</b> of the container, not the signature or decorators.<br>
     * A method for example should provide the ALL statements of the method body<br>
     * A class should provide ALL declared fields, methods and the constructor
     *
     * @return The list of all sub elements of this container.
     */
    public abstract TsElementList getContentList();

    @Override
    public T merge(T other) {
        return mergeContent(other);
    }

    public T mergeContent(T other) {
        return mergeContent(other, getContentResolver());
    }

    public T mergeContent(T other, BiConsumer<TsElementList, TsElementList> contentResolver) {
        TsElementList thisContentList = this.getContentList();
        contentResolver.accept(thisContentList, other.getContentList());
        TsElementList resolved = thisContentList.resolveDuplications();
        thisContentList.clear();
        thisContentList.addAll(resolved);
        return (T) this;
    }

    private BiConsumer<TsElementList, TsElementList> getContentResolver() {
        return this.contentResolver;
    }

    /**
     * Define your custom content resolver if two containers should be merged. The content
     * resolver ({@link BiConsumer}) has to define which of the given elements from
     * two should be removed and which of the elements should still remain in the merged
     * result. Usually container has to be merged, if the both have the same name.<br>
     * <br>
     * The default implementation will join both given lists so the result will
     * contain the content of both containers which are merged.<br>
     * If you would like to have a distinct list of elements, you can use the
     * {@link #removeDuplicatedElementsOnMerge()} function.
     *
     * @param contentResolver Takes two lists of elements which are defined in two container.
     *                        The Result of the {@link BiConsumer} will be printed in the
     *                        result.
     * @return Instance of this container
     * @see #removeDuplicatedElementsOnMerge()
     */
    public T setContentResolver(BiConsumer<TsElementList, TsElementList> contentResolver) {
        this.contentResolver = Objects.requireNonNull(contentResolver);
        return (T) this;
    }

    /**
     * Marks this container to remove duplicated sub elements if this container has to
     * be merged with another container.<br>
     * Example: You have two methods with the same name, and they contain elements which
     * are equal - lets say both declare a "console.log('Hello world')" - only one
     * element will remain in the merged method.
     * <hr>
     * <pre>{@code
     * TsMethod first = new TsMethod("foo").addContent("console.log('Hello world');")
     * TsMethod second = new TsMethod("foo").addContent("console.log('Hello world');")
     * TsMethod merged = first.merge(second);
     * String result = merged.build();
     * System.out.println(result);
     * }</pre>
     * Result:
     * <pre>{@code
     * foo(){
     *     console.log('Hello world);
     * }}</pre>
     * The content of the second 'foo' method is the same as from the first 'foo' method
     * and so it will be removed when the two methods gets merged.
     *
     * @return Ths instance of this container.
     */
    public T removeDuplicatedElementsOnMerge() {
        this.contentResolver = (first, second) -> {
            for (TsElement<?> element : second) {
                if (!first.contains(element)) {
                    first.add(element);
                }
            }
        };
        return (T) this;
    }
}
