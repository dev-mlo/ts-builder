package de.mlo.dev.tsbuilder.elements;

import de.mlo.dev.tsbuilder.elements.imports.TsImportList;
import lombok.Getter;

@Getter
public class TsContext {
    private final TsElementList elementList = new TsElementList();
    private final TsImportList importList = new TsImportList();
    private int indent = 2;

    public void add(TsElement element){
        this.elementList.add(element);
        this.importList.addAll(element.getImportList());
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public String compileImports() {
        return importList.build();
    }
}
