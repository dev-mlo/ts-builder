package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.type.TsTypes;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@Getter
public class ComplexValue extends TsElement<ComplexValue>{
    private final List<TsElement<?>> attributes = new ArrayList<>();

    public ComplexValue addValue(TsElement<?> attribute){
        this.attributes.add(attribute);
        return this;
    }

    public ComplexValue addValue(String name, TsElement<?> value){
        return addValue(new AttributeValuePair(name, value));
    }

    public ComplexValue addValue(AttributeValuePair attributeValuePair){
        return addValue((TsElement<?>)attributeValuePair);
    }

    public ComplexValue addStringValue(String name, String value){
        return addValue(new AttributeValuePair(name, new StringValue(value)));
    }

    public ComplexValue addStringArrayValue(String name, String... values){
        return addValue(new AttributeValuePair(name, new ArrayValue().addStrings(values)));
    }

    public ComplexValue addStringAttribute(String name){
        return addValue(name, TsTypes.STRING);
    }

    @Override
    public TsElementWriter<ComplexValue> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this, this::write);
    }

    private String write(TsContext context){
        return "{\n" + attributes.stream()
                .map(elem -> elem.build(context))
                .collect(Collectors.joining(",\n")).indent(context.getIndent()) + "}";
    }
}
