package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.type.TsTypes;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class TsFunctionReturnTypeList extends LinkedHashSet<TsElement<?>> {
    public String build(TsContext context) {
        return stream()
                .map(element -> element.build(context))
                .collect(Collectors.joining(" | "));
    }

    public void setOptional(boolean optional) {
        if (optional) {
            add(TsTypes.UNDEFINED);
        } else {
            remove(TsTypes.UNDEFINED);
        }
    }
}
