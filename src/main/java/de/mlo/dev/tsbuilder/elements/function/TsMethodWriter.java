package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecoratorList;

public class TsMethodWriter extends TsElementWriter<TsMethod> {
    protected TsMethodWriter(TsContext context, TsMethod element) {
        super(context, element);
    }

    @Override
    public String build() {
        String decorators = buildDecorators();
        String modifier = buildModifier();
        String parameter = buildParameter();
        String returnTypes = buildReturnTypes();
        String content = buildContent();
        return decorators +
                modifier + getElement().getName() + " (" + parameter + ")" + returnTypes + " {\n" +
                indent(content) +
                "}";
    }

    private String buildDecorators(){
        TsDecoratorList decoratorList = getElement().getDecoratorList();
        if(!decoratorList.isEmpty()){
            return decoratorList.build(getContext()) + "\n";
        }
        return "";
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
        return getElement()
                .getContentList()
                .build(getContext());
    }
}
