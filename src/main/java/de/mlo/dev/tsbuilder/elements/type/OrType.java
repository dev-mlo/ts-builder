package de.mlo.dev.tsbuilder.elements.type;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class OrType extends TsElement<OrType> {

    private final TsElement<?> first;
    private final TsElement<?> second;

    public OrType(TsElement<?> first, TsElement<?> second){
        this.first = first;
        this.second = second;
    }

    @Override
    public TsElementWriter<OrType> createWriter(TsContext context) {
        return new TsElementWriter<>(context, this) {
            @Override
            public String build() {
                return first.build(getContext()) + " | " + second.build(getContext());
            }
        };
    }
}
