package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;

public class TsFunctionWriter extends TsElementWriter<TsFunction> {
    protected TsFunctionWriter(TsContext context, TsFunction element) {
        super(context, element);
    }

    @Override
    public String build() {
        String modifier = buildModifier();
        String name = getElement().getName();
        String parameter = buildParameter();
        String returnTypes = buildReturnTypes();
        String content = buildContent();

        return modifier + "function " + name + " (" + parameter + ")" + returnTypes + " {\n" +
                indent(content) +
                "}";
    }

    private String buildModifier(){
        TsModifierList modifierList = getElement().getModifierList();
        if(!modifierList.isEmpty()){
            return modifierList.build(getContext()) + " ";
        }
        return "";
    }

    private String buildParameter(){
        return getElement().getParameterList().build(getContext());
    }

    private String buildReturnTypes(){
        TsFunctionReturnTypeList returnTypeList = getElement().getReturnTypeList();
        if(!returnTypeList.isEmpty()){
            return ": " + returnTypeList.build(getContext());
        }
        return "";
    }

    private String buildContent(){
        return getElement().getContentList().build(getContext());
    }
}
