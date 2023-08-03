package de.mlo.dev.tsbuilder.elements.clazz.constructor;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class TsConstructorParameterList extends LinkedHashSet<TsConstructorParameter> {
    public String build(TsContext context) {
        return stream()
                .map(element -> element.createWriter(context))
                .map(TsElementWriter::build)
                .collect(Collectors.joining(", "));
    }
}
