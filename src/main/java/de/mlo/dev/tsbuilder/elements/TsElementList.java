package de.mlo.dev.tsbuilder.elements;

import de.mlo.dev.tsbuilder.elements.function.TsMethod;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TsElementList extends ArrayList<TsElement<?>> {

    public String build(TsContext context) {
        return resolveDuplications()
                .stream()
                .map(element -> element.build(context))
                .collect(Collectors.joining("\n"));
    }

    /**
     * <p>
     * This function tries to automatically resolves duplicated elements.
     * For example: A class can have multiple methods but the name of
     * the methods must be unique. If the developer defines two exact same
     * methods we can automatically erase one of them. If the two methods
     * have the same declaration but different content, we can merge the
     * content together.
     * </p>
     * <p>
     * This automatism has its limits and in this case the elements will
     * throw a {@link MergeException}. The developer has to investigate,
     * why this happens. A {@link MergeException} for example will be
     * thrown if there are two methods with the same name but hast complete
     * different parameters.
     * </p>
     *
     * @return A resolved list without duplications
     * @throws MergeException if it is simply not possible to merge two
     * elements together
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    TsElementList resolveDuplications(){
        TsElementList merged = new TsElementList();
        TsElementList dropped = new TsElementList();
        for (int i = 0; i < size(); i++) {
            TsElement<?> first = get(i);
            if(dropped.contains(first)){
                continue;
            }

            // "i + 1" because previous elements has been already processed
            // and should be in the 'merged' list
            for (int j = i + 1; j < size(); j++) {
                TsElement<?> second = get(j);
                if(first instanceof TsDeclarativeElement firstContentMerger
                        && second instanceof TsDeclarativeElement secondContentMerger
                        && first.getClass().isAssignableFrom(second.getClass())
                        && firstContentMerger.isMergeRequired((TsDeclarativeElement) second)){
                    firstContentMerger.merge(secondContentMerger);
                    dropped.add(second);
                }
            }
            merged.add(first);
        }
        return merged;
    }

    @SuppressWarnings("unchecked")
    private <T> Stream<T> find(Class<T> elementType) {
        return stream()
                .filter(element -> elementType.isAssignableFrom(element.getClass()))
                .map(element -> (T) element);
    }

    public Optional<TsMethod> findMethod(String name){
        return find(TsMethod.class)
                .filter(method -> name.equals(method.getName()))
                .findFirst();
    }

    public Optional<TsMethod> findSetterMethod(String name){
        return find(TsMethod.class)
                .filter(method -> name.equals(method.getName()))
                .filter(TsMethod::isSetter)
                .findFirst();
    }

    public Optional<TsMethod> findGetterMethod(String name){
        return find(TsMethod.class)
                .filter(method -> name.equals(method.getName()))
                .filter(TsMethod::isGetter)
                .findFirst();
    }
}
