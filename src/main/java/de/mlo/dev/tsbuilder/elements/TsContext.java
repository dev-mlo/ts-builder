package de.mlo.dev.tsbuilder.elements;

import de.mlo.dev.tsbuilder.elements.imports.ITsImportWriter;
import de.mlo.dev.tsbuilder.elements.imports.TsImportList;
import de.mlo.dev.tsbuilder.elements.imports.TsImportWriter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class TsContext {
    private final TsElementList elementList = new TsElementList();
    private final TsImportList importList = new TsImportList();
    private ITsImportWriter importWriter = new TsImportWriter();
    private int indent = 2;

    public void add(TsElement<?> element){
        this.elementList.add(element);
        this.importList.addAll(element.getImportList());
    }

    public String compileImports() {
        return importWriter.write(this, importList);
    }
}
