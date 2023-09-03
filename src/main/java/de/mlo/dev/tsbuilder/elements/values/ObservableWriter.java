package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

import java.util.stream.Collectors;

public class ObservableWriter extends TsElementWriter<ObservableType> {
    protected ObservableWriter(TsContext context, ObservableType element) {
        super(context, element);
    }

    @Override
    public String build() {
        String generics = buildGenerics();
        return "Observable<" + generics + ">";
    }

    private String buildGenerics(){
        return getElement().getGenericTypeList().stream()
                .map(element -> element.build(getContext()))
                .collect(Collectors.joining(" | "));
    }
}
