package de.mlo.dev.tsbuilder.elements;

public class MergeException extends RuntimeException{
    public MergeException(String message) {
        super(message);
    }


    public static MergeException signatureMismatch(TsElement<?> first, TsElement<?> second){
        return new MergeException("""
                Unable to merge two elements because the declaration of those elments are different.
                First:  %s
                Second: %s"""
                .formatted(first.build(), second.build()));
    }
}
