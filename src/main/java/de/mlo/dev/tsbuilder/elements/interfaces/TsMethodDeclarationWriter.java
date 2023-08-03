package de.mlo.dev.tsbuilder.elements.interfaces;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import de.mlo.dev.tsbuilder.elements.function.TsFunctionParameterList;
import de.mlo.dev.tsbuilder.elements.function.TsFunctionReturnTypeList;

public class TsMethodDeclarationWriter extends TsElementWriter<TsMethodDeclaration> {
    protected TsMethodDeclarationWriter(TsContext context, TsMethodDeclaration element) {
        super(context, element);
    }

    @Override
    public String build() {
        String modifier = buildModifier();
        String name = getElement().getName();
        String parameter = buildParameter();
        String returnTypes = buildReturnTypes();
        return modifier + name + "(" + parameter + ")" + returnTypes + ";";
    }

    public String buildModifier(){
        TsModifierList modifierList = getElement().getModifierList();
        if(!modifierList.isEmpty()){
            return modifierList.build(getContext()) + " ";
        }
        return "";
    }

    public String buildParameter(){
        TsFunctionParameterList parameterList = getElement().getParameterList();
        if(!parameterList.isEmpty()){
            return parameterList.build(getContext());
        }
        return "";
    }

    public String buildReturnTypes(){
        TsFunctionReturnTypeList returnTypeList = getElement().getReturnTypeList();
        if(!returnTypeList.isEmpty()){
            return ": " + returnTypeList.build(getContext());
        }
        return ": void";
    }
}
