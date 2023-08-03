package de.mlo.dev.tsbuilder.elements.imports;

import de.mlo.dev.tsbuilder.elements.TsContext;

public interface ITsImportWriter {
    String write(TsContext context, TsImportList importList);
}
