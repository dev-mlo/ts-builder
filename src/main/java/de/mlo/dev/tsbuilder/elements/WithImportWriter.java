package de.mlo.dev.tsbuilder.elements;

import de.mlo.dev.tsbuilder.TsElementWriter;

public class WithImportWriter extends TsElementWriter<TsElement> {

    private final TsElementWriter<?> delegate;

    protected WithImportWriter(TsContext context, TsElement element, TsElementWriter<?> delegate) {
        super(context, element);
        this.delegate = delegate;
    }

    @Override
    public String build() {

        String content = delegate.build();

        String imports = getContext().compileImports();

        return imports + content;
    }
}
