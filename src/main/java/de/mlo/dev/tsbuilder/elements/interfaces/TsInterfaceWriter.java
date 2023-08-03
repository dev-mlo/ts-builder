package de.mlo.dev.tsbuilder.elements.interfaces;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;

public class TsInterfaceWriter extends TsElementWriter<TsInterface> {
    protected TsInterfaceWriter(TsContext context, TsInterface element) {
        super(context, element);
    }

    @Override
    public String build() {
        String modifier = buildModifier();
        String name = getElement().getName();
        String content = buildContent();

        return modifier + "interface " + name + "{\n" + indent(content) + "}";
    }

    public String buildModifier(){
        TsModifierList modifierList = getElement().getModifierList();
        if(!modifierList.isEmpty()){
            return modifierList.build(getContext()) + " ";
        }
        return "";
    }

    public String buildContent(){
        TsElementList elementList = getElement().getElementList();
        if(!elementList.isEmpty()){
            return elementList.build(getContext());
        }
        return "";
    }
}
