package de.mlo.dev.tsbuilder.elements.type;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class TsType extends TsElement<TsType> {

    private final TsModifierList modifierList = new TsModifierList();
    private final String name;
    private TsElement<?> value;

    public TsType(String name) {
        this.name = name;
    }

    public TsType setExport(){
        modifierList.setExport();
        return this;
    }

    public TsType setValue(TsElement<?> value) {
        this.value = value;
        return this;
    }

    public TsType setValue(ComplexType value){
        return setValue((TsElement<?>) value);
    }

    @Override
    public TsElementWriter<TsType> createWriter(TsContext context) {
        return new TsTypeWriter(context, this);
    }

}
