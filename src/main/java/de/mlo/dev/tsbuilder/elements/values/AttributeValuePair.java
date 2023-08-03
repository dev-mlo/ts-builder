package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class AttributeValuePair extends TsElement<AttributeValuePair> {

    private final String name;
    private final TsElement<?> value;

    public AttributeValuePair(String name, TsElement<?> value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public TsElementWriter<AttributeValuePair> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this, this::write);
    }

    private String write(TsContext context){
        return name + ": " + value.build(context);
    }
}
