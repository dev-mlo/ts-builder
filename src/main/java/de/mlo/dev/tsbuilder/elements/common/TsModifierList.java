package de.mlo.dev.tsbuilder.elements.common;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class TsModifierList extends LinkedHashSet<TsModifier> {

    public String build(TsContext context) {
        return stream()
                .map(modifier -> modifier.createWriter(context))
                .map(TsElementWriter::build)
                .collect(Collectors.joining(" "));
    }

    public void setDefault() {
        add(new TsModifier("default"));
    }

    public void setExport() {
        add(new TsModifier("export"));
    }

    /**
     * Marks the element as public so code outside a class can access it
     * <pre>{@code
     * public foo (): string {
     *     return 'bar'
     * }
     * }</pre>
     */
    public void setPublic() {
        add(new TsModifier("public"));
    }

    /**
     * Marks the element as <u>private</u> so code outside a class cannot access it
     * <pre>{@code
     * private foo (): string {
     *     return 'bar'
     * }
     * }</pre>
     */
    public void setPrivate() {
        add(new TsModifier("private"));
    }

    public void setSetter(){
        add(new TsModifier("set"));
    }

    /**
     * @return If this method is a setter like:
     * <pre>{@code
     * set value(value: string){
     *     this._value = value;
     * }
     * }</pre>
     */
    public boolean isSetter() {
        return contains(new TsModifier("set"));
    }

    public void setGetter(){
        add(new TsModifier("get"));
    }

    /**
     * @return If this method is a getter like:
     * <pre>{@code
     * get value(value: string){
     *     this._value = value;
     * }
     * }</pre>
     */
    public boolean isGetter() {
        return contains(new TsModifier("get"));
    }
}
