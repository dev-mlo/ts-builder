package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

/**
 * <pre>
 * param1: string | undefined, param2:
 * </pre>
 */
public class TsFunctionParameterList extends LinkedHashSet<TsFunctionParameter> {
    public String build(TsContext context) {
        return stream()
                .map(element -> element.createWriter(context))
                .map(TsElementWriter::build)
                .collect(Collectors.joining(", "));
    }
}
