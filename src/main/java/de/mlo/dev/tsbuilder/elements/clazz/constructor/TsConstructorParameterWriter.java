package de.mlo.dev.tsbuilder.elements.clazz.constructor;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;

public class TsConstructorParameterWriter extends TsElementWriter<TsConstructorParameter> {

    protected TsConstructorParameterWriter(TsContext context, TsConstructorParameter element) {
        super(context, element);
    }

    @Override
    public String build() {
        String modifiers = buildModifiers();
        String name = buildName();
        String type = buildType();

        return modifiers + name + ": " + type;
    }

    private String buildModifiers(){
        TsModifierList modifierList = getElement().getModifierList();
        if(!modifierList.isEmpty()){
            return modifierList.build(getContext()) + " ";
        }
        return "";
    }

    private String buildName(){
        String name = getElement().getName();
        if(getElement().isOptional()){
            return name + "?";
        }
        return name;
    }

    private String buildType(){
        TsElement type = getElement().getType();
        return type.build(getContext());
    }
}
