package de.mlo.dev.tsbuilder.elements.decorator;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class TsDecoratorPropertyList extends ArrayList<TsDecoratorProperty> {

    public String build(TsContext context){
        return stream()
                .map(property -> property.createWriter(context))
                .map(TsElementWriter::build)
                .collect(Collectors.joining(",\n"));
    }
}
