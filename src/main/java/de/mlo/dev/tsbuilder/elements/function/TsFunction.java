package de.mlo.dev.tsbuilder.elements.function;

import de.mlo.dev.tsbuilder.TsElementWriter;
import de.mlo.dev.tsbuilder.elements.*;
import de.mlo.dev.tsbuilder.elements.common.TsModifierList;
import de.mlo.dev.tsbuilder.elements.interfaces.TsMethodDeclaration;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * [modifiers]
 */
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Setter
@Getter
public class TsFunction extends TsElementContainer<TsFunction> {

    private final TsModifierList modifierList = new TsModifierList();
    private final TsFunctionParameterList parameterList = new TsFunctionParameterList();
    private final TsFunctionReturnTypeList returnTypeList = new TsFunctionReturnTypeList();
    private final TsElementList contentList = new TsElementList();
    private final String name;

    public TsFunction(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public TsFunction addContent(String rawContent){
        return addContent(TsElement.literal(rawContent));
    }

    public TsFunction addParameter(TsFunctionParameter parameter){
        this.parameterList.add(parameter);
        return this;
    }

    public TsFunction addContent(TsElement<?> element){
        this.contentList.add(element);
        return this;
    }

    public TsFunction addReturnType(TsFunctionReturnType returnType){
        return addReturnType((TsElement<?>) returnType);
    }

    public TsFunction addReturnType(TsElement<?> returnType){
        this.returnTypeList.add(returnType);
        return this;
    }

    public TsMethodDeclaration getMethodDeclaration() {
        return TsMethodDeclaration.fromFunction(this);
    }

    public TsFunction addComment(String comment){
        return addContent(TsElement.literal("// " + comment));
    }

    public TsFunction setExport(){
        this.modifierList.setExport();
        return this;
    }

    public TsFunction setExportDefault(){
        setExport();
        this.modifierList.setDefault();
        return this;
    }

    public TsFunction setSetter(){
        this.modifierList.setSetter();
        return this;
    }

    public TsFunction setGetter(){
        this.modifierList.setGetter();
        return this;
    }

    public boolean isSetter(){
        return modifierList.isSetter();
    }

    public boolean isGetter(){
        return modifierList.isGetter();
    }

    @Override
    public TsElementWriter<TsFunction> createWriter(TsContext context) {
        return new TsFunctionWriter(context, this);
    }

    /**
     * In TypeScript it is not allowed to have multiple functions with the same name
     *
     * @param other The other function to check
     * @return true if this and the other function must be merged
     */
    public boolean isMergeRequired(TsFunction other){
        return getName().equals(other.getName())
                && isGetter() == other.isGetter()
                && isSetter() == other.isSetter();
    }

    @Override
    public TsFunction merge(TsFunction other) {
        TsMethodDeclaration thisDeclaration = getMethodDeclaration();
        TsMethodDeclaration otherDeclaration = other.getMethodDeclaration();
        if(thisDeclaration.equals(otherDeclaration)){
            return super.merge(other);
        }
        throw MergeException.signatureMismatch(thisDeclaration, otherDeclaration);
    }
}
