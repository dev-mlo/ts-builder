package de.mlo.dev.tsbuilder.elements.decorator;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;

public class TsDecoratorList extends LinkedHashSet<TsDecorator> {

    public String build(TsContext context){
        return stream()
                .map(decorator -> decorator.createWriter(context))
                .map(TsElementWriter::build)
                .collect(Collectors.joining("\n"));
    }

    public Optional<TsDecorator> get(String decoratorName){
        return stream()
                .filter(tsDecorator -> decoratorName.equals(tsDecorator.getName()))
                .findFirst();
    }
}
