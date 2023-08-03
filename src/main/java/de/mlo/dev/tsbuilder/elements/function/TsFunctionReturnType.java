package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@EqualsAndHashCode(callSuper = false)
@Getter
public class TsFunctionReturnType extends TsElement<TsFunctionReturnType> {
    private final String name;

    public TsFunctionReturnType(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public TsElementWriter<TsFunctionReturnType> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this, () -> name);
    }
}
