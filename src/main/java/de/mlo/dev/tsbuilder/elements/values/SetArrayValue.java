package de.mlo.dev.tsbuilder.elements.values;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@Getter
public class SetArrayValue extends TsElement {

    private final Set<TsElement> values = new LinkedHashSet<>();

    public SetArrayValue addString(String value){
        return add(new StringValue(value));
    }

    public SetArrayValue addStrings(String... value){
        return addStrings(Arrays.asList(value));
    }

    public SetArrayValue addStrings(Collection<String> value){
        value.forEach(this::addString);
        return this;
    }

    public SetArrayValue add(TsElement element){
        this.values.add(element);
        return this;
    }

    @Override
    public TsElementWriter<?> createWriter(TsContext context) {
        return TsElementWriter.wrap(context, this, this::write);
    }

    public String write(TsContext context){
        if(values.size() > 1){
            return writeMultiValueString(context);
        }
        return writeSingleValueString(context);
    }

    private String writeMultiValueString(TsContext context){
        String values = writeValue(context, ",\n");
        return "[\n"+ values.indent(context.getIndent()) + "]";
    }

    private String writeSingleValueString(TsContext context){
        return "[" + writeValue(context, ", ") + "]";
    }

    private String writeValue(TsContext context, String valueSeparator){
        return values.stream()
                .map(elem -> elem.build(context))
                .collect(Collectors.joining(valueSeparator));
    }
}
