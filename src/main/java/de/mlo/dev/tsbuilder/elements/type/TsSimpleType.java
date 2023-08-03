package de.mlo.dev.tsbuilder.elements.type;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class TsSimpleType extends TsElement<TsSimpleType> {

    private final String name;

    public TsSimpleType(String name) {
        this.name = name;
    }

    public TsSimpleType toArray(){
        if(name.endsWith("[]")){
            return new TsSimpleType(name);
        }
        return new TsSimpleType(name + "[]");
    }

    @Override
    public TsElementWriter<TsSimpleType> createWriter(TsContext context) {
        return new TsElementWriter<>(context, this) {
            @Override
            public String build() {
                return getElement().name;
            }
        };
    }
}
