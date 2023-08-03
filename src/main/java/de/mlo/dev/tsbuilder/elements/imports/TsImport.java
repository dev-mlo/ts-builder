package de.mlo.dev.tsbuilder.elements.imports;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * import {Component_1, Component_2} from 'path_to_module';
 */
@Getter
@EqualsAndHashCode
public class TsImport {
    private final Set<String> moduleNames = new LinkedHashSet<>();
    private final String fromPath;
    private final Set<TsImport> dependencies = new LinkedHashSet<>();

    public TsImport(String moduleName, String fromPath) {
        this.moduleNames.add(moduleName);
        this.fromPath = fromPath;
    }

    public TsImport addModuleName(String moduleName){
        this.moduleNames.add(moduleName);
        return this;
    }

    public TsImport addDependency(TsImport dependency){
        this.dependencies.add(dependency);
        return this;
    }
}
