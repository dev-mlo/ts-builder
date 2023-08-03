package de.mlo.dev.tsbuilder.elements;

public abstract class TsDeclarativeElement<E extends TsDeclarativeElement<E>> extends TsElement<E> {
    public TsDeclarativeElement() {
    }

    public TsDeclarativeElement(E element) {
        super(element);
    }

    public abstract boolean isMergeRequired(E other);

    public abstract E merge(E other);
}
