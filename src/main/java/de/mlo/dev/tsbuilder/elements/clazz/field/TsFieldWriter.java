package de.mlo.dev.tsbuilder.elements.clazz.field;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.decorator.TsDecoratorList;

public class TsFieldWriter extends TsElementWriter<TsField> {

    protected TsFieldWriter(TsContext context, TsField element) {
        super(context, element);
    }

    @Override
    public String build(){
        String decorators = buildDecorators();
        String name = buildName();
        String type = buildType();
        String value = buildValue();
        return decorators + name + type + value +";";
    }

    private String buildDecorators(){
        TsDecoratorList decoratorList = getElement().getDecoratorList();
        if(!decoratorList.isEmpty()){
            return decoratorList.build(getContext()) + "\n";
        }
        return "";
    }

    private String buildName(){
        String name = getElement().getName();
        if(getElement().isOptional()){
            name = name + "?";
        } else if(getElement().isNeverNull()){
            name = name + "!";
        }
        return getElement().getNamePrefix() + name;
    }

    private String buildType(){
        TsElement<?> type = getElement().getType();
        if(type != null){
            return ": " + type.build(getContext());
        }
        return "";
    }

    private String buildValue(){
        TsElement<?> value = getElement().getValue();
        if(value != null){
            return " = " + value.build();
        }
        return "";
    }
}
