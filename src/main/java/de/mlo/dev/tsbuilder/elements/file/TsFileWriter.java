package de.mlo.dev.tsbuilder.elements.file;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

import java.util.stream.Collectors;

public class TsFileWriter extends TsElementWriter<TsFile> {
    protected TsFileWriter(TsContext context, TsFile element) {
        super(context, element);
    }

    @Override
    public String build() {
        return getElement().getContentList()
                .stream()
                .map(element -> element.build(getContext()))
                .collect(Collectors.joining("\n"));
    }
}
