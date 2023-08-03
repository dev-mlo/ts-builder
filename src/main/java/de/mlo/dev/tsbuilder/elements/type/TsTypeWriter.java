package de.mlo.dev.tsbuilder.elements.type;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;

public class TsTypeWriter extends TsElementWriter<TsType> {

    protected TsTypeWriter(TsContext context, TsType element) {
        super(context, element);
    }

    @Override
    public String build() {
        String modifiers = buildModifiers();
        String name = getElement().getName();
        String type = buildType();
        return modifiers + "type " + name + " = " + type;
    }

    public String buildModifiers(){
        TsModifierList modifierList = getElement().getModifierList();
        if(!modifierList.isEmpty()){
            return modifierList.build(getContext()) + " ";
        }
        return "";
    }

    public String buildType(){
        TsElement<?> value = getElement().getValue();
        return value.build(getContext());
    }
}
