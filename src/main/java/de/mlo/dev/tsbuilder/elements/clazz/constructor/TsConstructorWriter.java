package de.mlo.dev.tsbuilder.elements.clazz.constructor;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

public class TsConstructorWriter extends TsElementWriter<TsConstructor> {

    protected TsConstructorWriter(TsContext context, TsConstructor element) {
        super(context, element);
    }

    @Override
    public String build() {
        String parameter = buildParameter();
        String content = buildContent();
        return "constructor (" + parameter + ") {\n" + indent(content) + "}";
    }

    private String buildParameter(){
        return getElement().getParameterList().build(getContext());
    }

    private String buildContent(){
        return getElement().getContentList().build(getContext());
    }
}
