package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class StringValue extends TsElement {

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public TsElementWriter<?> createWriter(TsContext context) {
        return new TsElementWriter<TsElement>(context, this) {
            @Override
            public String build() {
                return "'" + value + "'";
            }
        };
    }
}
