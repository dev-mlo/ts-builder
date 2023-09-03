package de.mlo.dev.tsbuilder.elements.file;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElementContainer;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * An unnamed alternative to the {@link TsFile}
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class TsBlock extends TsElementContainer<TsBlock> {

    private final TsElementList contentList = new TsElementList();

    @Override
    protected TsElementWriter<TsBlock> createWriter(TsContext context) {
        return new TsBlockWriter(context, this);
    }

    @Override
    public boolean isMergeRequired(TsBlock other) {
        return true;
    }
}
