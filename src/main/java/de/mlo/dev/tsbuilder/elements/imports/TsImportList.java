package de.mlo.dev.tsbuilder.elements.imports;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TsImportList extends LinkedHashSet<TsImport> {

    public String build(){
        Map<String, Set<TsImport>> map = flattenAndDistinctImports();
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

    /**
     * Walk through every added import and its dependencies and puts them into
     * a map. The key of the map is the path ('from') to the module. One path
     * can have multiple elements to import. Duplicated imports will be removed.
     *
     * @return A map with all declared imports
     */
    private Map<String, Set<TsImport>> flattenAndDistinctImports(){
        Map<String, Set<TsImport>> map = new LinkedHashMap<>();
        forEach(tsImport -> distinct(map, tsImport));
        return map;
    }

    private void distinct(Map<String, Set<TsImport>> map, TsImport tsImport){
        Set<TsImport> tsImports = map.computeIfAbsent(tsImport.getFromPath(), path -> new LinkedHashSet<>());
        tsImports.add(tsImport);
        tsImport.getDependencies().forEach(dependency -> distinct(map, dependency));
    }
}
