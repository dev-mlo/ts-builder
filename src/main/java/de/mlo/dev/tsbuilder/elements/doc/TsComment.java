package de.mlo.dev.tsbuilder.elements.doc;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TsComment extends TsElement<TsComment> {

    private final List<String> commentLines = new ArrayList<>();

    public TsComment(String comment){
        addComment(comment);
    }

    public TsComment addComment(String comment){
        this.commentLines.add(comment);
        return this;
    }

    @Override
    protected TsElementWriter<TsComment> createWriter(TsContext context) {
        return new TsCommentWriter(context, this);
    }
}
