package de.mlo.dev.tsbuilder.elements.type;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@Getter
public class ComplexType extends TsElement<ComplexType> {

    private final List<TsElement<?>> attributes = new ArrayList<>();

    public ComplexType addAttribute(TsElement<?> attribute){
        this.attributes.add(attribute);
        return this;
    }

    public ComplexType addAttribute(String name, TsElement<?> type){
        return addAttribute(new AttributeTypePair(name, type));
    }

    public ComplexType addStringAttribute(String name){
        return addAttribute(name, TsTypes.STRING);
    }

    @Override
    public TsElementWriter<ComplexType> createWriter(TsContext context) {
        return new TsElementWriter<>(context, this) {
            @Override
            public String build() {
                return "{\n" + attributes.stream()
                        .map(elem -> elem.build(context))
                        .collect(Collectors.joining(",\n")).indent(getContext().getIndent()) + "}";
            }
        };
    }
}
