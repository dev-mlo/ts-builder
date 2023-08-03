package de.mlo.dev.tsbuilder.elements.clazz;

import de.mlo.dev.tsbuilder.elements.TsContext;

import java.util.LinkedHashSet;

public class TsImplementsList extends LinkedHashSet<String> {

    public String build(TsContext context) {
        return String.join(", ", this);
    }
}
