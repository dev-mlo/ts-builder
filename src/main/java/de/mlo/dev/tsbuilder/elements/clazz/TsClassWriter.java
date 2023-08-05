package de.mlo.dev.tsbuilder.elements.clazz;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecoratorList;

public class TsClassWriter extends TsElementWriter<TsClass> {
    protected TsClassWriter(TsContext context, TsClass element) {
        super(context, element);
    }

    @Override
    public String build() {
        String decorators = buildDecorators();
        String modifier = buildModifier();
        String name = buildName();
        String superClass = buildSuperClass();
        String implementz = buildImplements();
        String content = buildContent();

        return decorators +
                modifier + "class " + name + superClass + implementz + "{\n" +
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

    private String buildName(){
        String name = getElement().getName();
        if(name != null && !name.isBlank()){
            return name;
        }
        return "";
    }

    private String buildSuperClass(){
        if(getElement().getSuperClassName() != null){
            return " extends " + getElement().getSuperClassName();
        }
        return "";
    }

    private String buildImplements(){
        TsImplementsList implementsList = getElement().getImplementsList();
        if(!implementsList.isEmpty()){
            return " implements " + implementsList.build(getContext());
        }
        return "";
    }

    private String buildContent(){
        return getElement().getContentList().build(getContext());
    }
}
