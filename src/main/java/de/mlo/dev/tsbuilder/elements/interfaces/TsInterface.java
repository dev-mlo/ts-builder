package de.mlo.dev.tsbuilder.elements.interfaces;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class TsInterface extends TsElement<TsInterface> {

    private final TsModifierList modifierList = new TsModifierList();
    private final TsElementList elementList = new TsElementList();
    private final String name;

    public TsInterface(String name) {
        this.name = name;
    }

    public TsInterface add(TsElement<?> element){
        this.elementList.add(element);
        return this;
    }

    public TsInterface addMethod(TsMethodDeclaration method){
        return add(method);
    }

    public TsInterface setExport(){
        modifierList.setExport();
        return this;
    }

    @Override
    public TsElementWriter<TsInterface> createWriter(TsContext context) {
        return new TsInterfaceWriter(context, this);
    }

}
