package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@Getter
public class ArrayValue  extends TsElement<ArrayValue> {

    private final List<TsElement<?>> values = new ArrayList<>();

    public ArrayValue addString(String value){
        return add(new StringValue(value));
    }

    public ArrayValue addStrings(String... value){
        return addStrings(Arrays.asList(value));
    }

    public ArrayValue addStrings(Collection<String> value){
        value.forEach(this::addString);
        return this;
    }

    public ArrayValue addNumber(String value){
        return add(new NumberValue(value));
    }

    public ArrayValue addNumbers(String... values){
        return addNumbersAsString(Arrays.asList(values));
    }

    public ArrayValue addNumbersAsString(Collection<String> values){
        values.forEach(this::addNumber);
        return this;
    }

    public ArrayValue addNumber(Number value){
        return add(new NumberValue(value));
    }

    public ArrayValue addNumbers(Number... values){
        return addNumbers(Arrays.asList(values));
    }

    public ArrayValue addNumbers(Collection<Number> values){
        values.forEach(this::addNumber);
        return this;
    }

    public ArrayValue addComplexValues(ComplexValue... values){
        return addComplexValues(Arrays.asList(values));
    }

    public ArrayValue addComplexValues(Collection<ComplexValue> value){
        value.forEach(this::add);
        return this;
    }

    public ArrayValue add(TsElement element){
        this.values.add(element);
        return this;
    }

    @Override
    public TsElementWriter<ArrayValue> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this, this::write);
    }

    private String write(TsContext context){
        return "[" + values.stream()
                .map(elem -> elem.build(context))
                .collect(Collectors.joining(", ")) + "]";
    }
}
