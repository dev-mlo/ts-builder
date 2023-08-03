package de.mlo.dev.tsbuilder.elements;

public enum DuplicationStrategy {

    /**
     * Just do noting
     */
    NONE,

    /**
     * Merges the content of two element
     */
    MERGE,

    /**
     * Drops the last added element if there is already an element of the same
     * type with the same name
     */
    DROP_IF_NAME_IS_EQUALS,

    /**
     * Drops the last added element if there is already an element with the
     * same signature.
     */
    DROP_IF_DECLARATION_IS_EQUAL,

    /**
     * Drops an element if the content of two elements are equal. If the declaration
     * of the two elements are the same but the content is different, both elements
     * will be printed. Usually this will lead to a compile error because of
     * duplicated names. This option is good to debug your generator.
     */
    DROP_IF_CONTENT_IS_EQUAL
}
