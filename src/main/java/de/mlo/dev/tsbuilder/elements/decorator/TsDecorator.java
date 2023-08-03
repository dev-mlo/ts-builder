package de.mlo.dev.tsbuilder.elements.decorator;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

/**
 * <p>
 * A decorator is the equivalent to an annotation in Java.
 * </p>
 * Example of a component decorator in Angular:
 * <pre>{@code
 * @Component({
 *  selector: 'app-custom-component',
 *  templateUrl: './custom.component.html',
 *  styleUrls: ['custom.component.scss'],
 * })
 * }</pre>
 */
@EqualsAndHashCode(callSuper = false)
@Getter
public class TsDecorator extends TsElement<TsDecorator> {
    private final TsDecoratorPropertyList decoratorPropertyList = new TsDecoratorPropertyList();

    private final String name;

    /**
     * Creates a new decorator instance.
     *
     * @param name The name of the decorator. If the name does not
     *             start with an "@" it will be automatically added.
     */
    public TsDecorator(String name) {
        Objects.requireNonNull(name);
        if(name.charAt(0) == '@'){
            this.name = name;
        } else {
            this.name = "@" + name;
        }
    }

    @Override
    public TsElementWriter<TsDecorator> createWriter(TsContext context) {
        return new TsDecoratorWriter(context, this);
    }

    public TsDecorator addProperty(TsDecoratorProperty property){
        this.decoratorPropertyList.add(property);
        return this;
    }

    public TsDecorator addProperty(String name, String literalValue){
        return addProperty(new TsDecoratorProperty()
                .setName(name)
                .setValue(TsElement.literal(literalValue)));
    }

    public TsDecorator addStringProperty(String name, String value){
        return addProperty(new TsDecoratorProperty()
                .setName(name)
                .setStringValue(value));
    }
}
