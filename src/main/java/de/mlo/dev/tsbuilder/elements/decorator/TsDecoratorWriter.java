package de.mlo.dev.tsbuilder.elements.decorator;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

public class TsDecoratorWriter extends TsElementWriter<TsDecorator> {
    protected TsDecoratorWriter(TsContext context, TsDecorator element) {
        super(context, element);
    }

    @Override
    public String build() {
        String name = getElement().getName();
        String properties = buildProperties();

        String result = name + properties;
        if(getElement().isPreventLineBreaks()){
            result = result.indent(Integer.MIN_VALUE).replace("\n", "");
        }
        return result;
    }

    private String buildProperties(){
        TsDecoratorPropertyList decoratorPropertyList = getElement().getDecoratorPropertyList();
        if(!decoratorPropertyList.isEmpty()){
            return "(\n" + decoratorPropertyList.build(getContext()).indent(getIndent()) + ")";
        }
        return "()";
    }
}
