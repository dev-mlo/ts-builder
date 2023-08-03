package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;

public class TsFunctionParameterWriter extends TsElementWriter<TsFunctionParameter> {
    protected TsFunctionParameterWriter(TsContext context, TsFunctionParameter element) {
        super(context, element);
    }

    @Override
    public String build() {
        String name = buildName();
        String type = buildType();
        return name + ": " + type;
    }

    private String buildName(){
        String name = getElement().getName();
        if(getElement().isOptional()){
            name = name + "?";
        }
        return name;
    }

    public String buildType(){
        TsElement<?> type = getElement().getType();
        return type.build(getContext());
    }
}
