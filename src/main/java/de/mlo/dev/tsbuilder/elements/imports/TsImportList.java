package de.mlo.dev.tsbuilder.elements.imports;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TsImportList extends LinkedHashSet<TsImport> {

    public String build(){
        Map<String, Set<TsImport>> map = new LinkedHashMap<>();
        for(TsImport tsImport : this){
            Set<TsImport> tsImports = map.computeIfAbsent(tsImport.getFromPath(), path -> new LinkedHashSet<>());
            tsImports.add(tsImport);
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Set<TsImport>> entry : map.entrySet()) {
            String modules = entry.getValue()
                    .stream()
                    .map(TsImport::getModuleNames)
                    .flatMap(Set::stream)
                    .collect(Collectors.joining(", "));
            sb.append("import {").append(modules).append("} from '").append(entry.getKey()).append("';\n");
        }
        sb.append('\n');
        return sb.toString();
    }
}
