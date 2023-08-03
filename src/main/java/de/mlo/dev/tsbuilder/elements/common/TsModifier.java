package de.mlo.dev.tsbuilder.elements.common;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class TsModifier extends TsElement<TsModifier> {
    private final String name;

    public TsModifier(String name) {
        this.name = name;
    }

    @Override
    public TsElementWriter<TsModifier> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this, () -> name);
    }
}
