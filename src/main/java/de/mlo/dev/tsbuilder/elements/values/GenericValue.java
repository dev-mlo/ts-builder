package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class GenericValue extends TsElement{
    private final TsElementList genericTypeList = new TsElementList();
    private final String name;

    public GenericValue(String name) {
        this.name = name;
    }

    public GenericValue addGeneric(TsElement element){
        this.genericTypeList.add(element);
        return this;
    }

    @Override
    public TsElementWriter<?> createWriter(TsContext context) {
        return new GenericValueWriter(context, this);
    }
}
