package de.mlo.dev.tsbuilder.elements.decorator;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;

public class TsDecoratorPropertyWriter extends TsElementWriter<TsDecoratorProperty> {
    protected TsDecoratorPropertyWriter(TsContext context, TsDecoratorProperty element) {
        super(context, element);
    }

    @Override
    public String build() {
        String name = buildName();
        String value = buildValue();
        return name + value;
    }

    private String buildName(){
        String name = getElement().getName();
        if(name != null && !name.isBlank()){
            return name + ": ";
        }
        return "";
    }

    private String buildValue(){
        TsElement<?> value = getElement().getValue();
        return value.build(getContext());
    }
}
