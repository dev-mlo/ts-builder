package de.mlo.dev.tsbuilder;

import de.mlo.dev.tsbuilder.elements.TsContext;
import de.mlo.dev.tsbuilder.elements.TsElement;
import de.mlo.dev.tsbuilder.elements.TsElementList;
import de.mlo.dev.tsbuilder.elements.values.Literal;
import lombok.Getter;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@Getter
public abstract class TsElementWriter<E extends TsElement<?>> {
    public final E element;
    private final TsContext context;

    protected TsElementWriter(TsContext context, E element) {
        this.context = context;
        this.element = element;
        this.context.add(element);
    }

    public static <E extends TsElement<E>> TsElementWriter<E> wrap(TsContext context, E inElement, Supplier<String> writeFunction) {
        return new TsElementWriter<E>(context, inElement) {
            @Override
            public String build() {
                String beforeElementContent = buildBeforeElementContent();
                String content = writeFunction.get();
                String afterElementContent = buildAfterElementContent();
                return beforeElementContent + content + afterElementContent;
            }
        };
    }

    public static <E extends TsElement<E>> TsElementWriter<E> wrap(TsContext context, E inElement, Function<TsContext, String> writeFunction) {
        return new TsElementWriter<E>(context, inElement) {
            @Override
            public String build() {
                String beforeElementContent = buildBeforeElementContent();
                String content = writeFunction.apply(getContext());
                String afterElementContent = buildAfterElementContent();
                return beforeElementContent + content + afterElementContent;
            }
        };
    }

    public static <E extends TsElement<E>> TsElementWriter<E> wrap(TsContext context, E inElement, BiFunction<TsContext, E, String> writeFunction) {
        return new TsElementWriter<E>(context, inElement) {
            @Override
            public String build() {
                String beforeElementContent = buildBeforeElementContent();
                String content = writeFunction.apply(getContext(), inElement);
                String afterElementContent = buildAfterElementContent();
                return beforeElementContent + content + afterElementContent;
            }
        };
    }

    public static TsElementWriter<Literal> literal(TsContext context, String literal) {
        return new TsElementWriter<>(context, new Literal(literal)) {
            @Override
            public String build() {
                return literal;
            }
        };
    }

    public int getIndent() {
        return getContext().getIndent();
    }

    public String indent(String text) {
        return text.indent(getIndent());
    }

    public abstract String build();

    public String buildBeforeElementContent() {
        TsElementList beforeElementContent = getElement().getBeforeElementContent();
        if (!beforeElementContent.isEmpty()) {
            return beforeElementContent.build(getContext()) + "\n";
        }
        return "";
    }

    public String buildAfterElementContent() {
        TsElementList afterElementContent = getElement().getAfterElementContent();
        if (!afterElementContent.isEmpty()) {
            return "\n" + afterElementContent.build(getContext());
        }
        return "";
    }
}
