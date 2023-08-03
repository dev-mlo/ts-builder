package de.mlo.dev.tsbuilder.elements.type;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class AttributeTypePair extends TsElement<AttributeTypePair> {

    private final String name;
    private final TsElement<?> value;

    public AttributeTypePair(String name, TsElement<?> value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public TsElementWriter<AttributeTypePair> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this,
                () -> name + ": " + value.build(context));
    }
}
