package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = false)
@Getter
public class GenericValue extends TsElement<GenericValue>{
    private final TsElementList genericTypeList = new TsElementList();
    private final String name;

    public GenericValue(String name) {
        this.name = name;
    }

    /**
     * Add a new generic. Multiple generics will be connected with '|'.
     * <pre>{@code
     *     MyValue
     *     --> addGeneric("string")
     *     MyValue<string>
     *     --> addGeneric("number")
     *     MyValue<string | number>
     * }</pre>
     *
     * @param literal The name of the generic type (can also be something complex)
     * @return The instance of this {@link GenericValue}
     */
    public GenericValue addGeneric(String literal){
        return addGeneric(TsElement.literal(literal));
    }

    public GenericValue addGeneric(TsElement<?> element){
        this.genericTypeList.add(element);
        return this;
    }

    @Override
    public TsElementWriter<GenericValue> createWriter(TsContext context) {
        return new GenericValueWriter(context, this);
    }
}
