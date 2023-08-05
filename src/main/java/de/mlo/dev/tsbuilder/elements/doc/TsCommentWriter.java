package de.mlo.dev.tsbuilder.elements.doc;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

public class TsCommentWriter extends TsElementWriter<TsComment> {
    protected TsCommentWriter(TsContext context, TsComment element) {
        super(context, element);
    }

    @Override
    public String build() {
        return String.join("\n", getElement().getCommentLines());
    }
}
