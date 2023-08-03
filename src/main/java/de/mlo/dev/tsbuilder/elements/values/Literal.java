package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class Literal extends TsElement<Literal> {

    private final String literal;

    public Literal(String literal) {
        this.literal = literal;
    }

    @Override
    public TsElementWriter<Literal> createWriter(TsContext context) {
        return TsElementWriter.literal(context, literal);
    }
}
