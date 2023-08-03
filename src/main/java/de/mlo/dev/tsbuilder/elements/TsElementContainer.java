package de.mlo.dev.tsbuilder.elements;

import java.util.Objects;
import java.util.function.BiConsumer;

@SuppressWarnings("unchecked")
public abstract class TsElementContainer<T extends TsElementContainer<T>> extends TsDeclarativeElement<T> implements ContentMerger<T>{

    private BiConsumer<TsElementList, TsElementList> contentResolver = (TsElementList list, TsElementList c) -> {
        if(!list.equals(c)){
            list.addAll(c);
        }
    };

    public TsElementContainer() {
    }

    public TsElementContainer(T element) {
        super(element);
    }

    public abstract TsElementList getContentList();

    @Override
    public T merge(T other) {
        return mergeContent(other);
    }

    public T mergeContent(T other){
        return mergeContent(other, getContentResolver());
    }


    public T mergeContent(T other, BiConsumer<TsElementList, TsElementList> contentResolver){
        TsElementList thisContentList = this.getContentList();
        contentResolver.accept(thisContentList, other.getContentList());
        TsElementList resolved = thisContentList.resolveDuplications();
        thisContentList.clear();
        thisContentList.addAll(resolved);
        return (T) this;
    }

    private BiConsumer<TsElementList, TsElementList> getContentResolver(){
         return this.contentResolver;
    }

    public T setContentResolver(BiConsumer<TsElementList, TsElementList> contentResolver) {
        this.contentResolver = Objects.requireNonNull(contentResolver);
        return (T) this;
    }

    public T removeDuplicatedElementsOnMerge(){
        this.contentResolver = (first, second) -> {
            for (TsElement<?> element : second) {
                if(!first.contains(element)){
                    first.add(element);
                }
            }
        };
        return (T) this;
    }
}
