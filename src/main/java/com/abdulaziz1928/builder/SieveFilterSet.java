package com.abdulaziz1928.builder;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class SieveFilterSet {
    final List<SieveBuilder> filterSet = new ArrayList<>();

    public String generateScript() throws IOException {
        var filters = generateFilters();
        var imports = generateImports();
        return imports + filters;
    }

    public void appendFilter(SieveBuilder filter) {
        filterSet.add(filter);
    }

    private String generateFilters() throws IOException {
        var filterValue = "";
        for (var filter : filterSet)
            filterValue = filterValue.concat(filter.generateScript());

        return filterValue;
    }

    private String generateImports() {
        var imports = new HashSet<String>();
        filterSet.forEach(filter ->
                imports.addAll(filter.getImports().getCapabilities()));
        return require(imports.stream().toList());
    }

    private String require(List<String> imports) {
        if (imports.isEmpty())
            return "";
        var capabilities = getListAsString(imports);
        return String.format("require %s;\n", capabilities);
    }

    static String getListAsString(List<String> list) {
        var format = "\"%s\"";
        if (list.size() == 1) {
            return String.format(format, list.get(0));
        }
        var value = list.stream().map(val -> String.format(format, val)).collect(Collectors.joining(", "));
        return String.format("[%s]", value);
    }
}
