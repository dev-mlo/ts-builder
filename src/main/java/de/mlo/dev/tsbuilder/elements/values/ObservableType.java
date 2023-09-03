package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import de.mlo.dev.tsbuilder.elements.imports.TsImports;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;

@EqualsAndHashCode(callSuper = false)
@Getter
public class ObservableType extends TsElement<ObservableType> {

    private final TsElementList genericTypeList = new TsElementList();

    public ObservableType(){
        addImport(TsImports.Rx.OBSERVABLE);
    }

    public ObservableType(String type){
        this();
        addType(type);
    }

    public ObservableType(TsElement<?> type){
        this();
        addTypes(type);
    }

    public ObservableType addType(String type){
        return addTypes(new Literal(type));
    }

    public ObservableType addTypes(TsElement<?>... types){
        return addTypes(Arrays.asList(types));
    }

    public ObservableType addTypes(Collection<TsElement<?>> types){
        this.genericTypeList.addAll(types);
        return this;
    }

    @Override
    protected TsElementWriter<ObservableType> createWriter(TsContext context) {
        return new ObservableWriter(context, this);
    }
}
