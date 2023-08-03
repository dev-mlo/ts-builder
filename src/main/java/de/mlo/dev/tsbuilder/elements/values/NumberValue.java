package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class NumberValue  extends TsElement<NumberValue> {

    private final String value;

    public NumberValue(Number value) {
        this(value.toString());
    }

    public NumberValue(String value) {
        this.value = value;
    }

    @Override
    public TsElementWriter<NumberValue> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this, () -> value);
    }
}
