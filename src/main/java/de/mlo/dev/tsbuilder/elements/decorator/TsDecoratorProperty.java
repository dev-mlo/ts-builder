package de.mlo.dev.tsbuilder.elements.decorator;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.values.StringValue;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class TsDecoratorProperty extends TsElement<TsDecoratorProperty> {

    private String name;
    private TsElement<?> value;

    public TsDecoratorProperty setStringValue(String value){
        return setValue(new StringValue(value));
    }

    @Override
    public TsElementWriter<TsDecoratorProperty> createWriter(TsContext context) {
        return new TsDecoratorPropertyWriter(context, this);
    }
}
