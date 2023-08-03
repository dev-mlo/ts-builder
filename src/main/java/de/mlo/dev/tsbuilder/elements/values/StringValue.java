package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class StringValue extends TsElement<StringValue> {

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public TsElementWriter<StringValue> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this, () -> "'" + value + "'");
    }
}
