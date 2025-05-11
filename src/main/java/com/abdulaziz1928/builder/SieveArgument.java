package com.abdulaziz1928.builder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

interface Writable {
    void write(OutputStream os) throws IOException;
}

public class SieveArgument implements Writable {
    private final List<Writable> items = new ArrayList<>();

    public SieveArgument writeString(String s) {
        items.add(new QuotedString(s));
        return this;
    }

    public SieveArgument writeStringList(List<String> s) {
        items.add(new StringList(s));
        return this;
    }
    public SieveArgument writeConditionBlock(String name, SieveArgument condition, List<SieveArgument> actions) {
        items.add(new ConditionalBlock(name, condition, actions));
        return this;
    }

    public SieveArgument writeAtom(String s) {
        items.add(new Atom(s));
        return this;
    }

    public SieveArgument writeNumber(int i) {
        items.add(new NumberArg(i));
        return this;
    }

    public SieveArgument writeNumber(long i) {
        items.add(new NumberArg(i));
        return this;
    }

    public SieveArgument writeTest(String name, List<SieveArgument> args) {
        items.add(new TestOperator(name, args));
        return this;
    }

    public SieveArgument appendArgument(SieveArgument arg) {
        items.addAll(arg.items);
        return this;
    }

    @Override
    public void write(OutputStream os) throws IOException {
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                os.write(' ');
            }
            items.get(i).write(os);
        }
    }

    private static class Atom implements Writable {
        final String value;

        Atom(String value) {
            this.value = value;
        }

        @Override
        public void write(OutputStream os) throws IOException {
            os.write(value.getBytes(StandardCharsets.UTF_8));
        }
    }

    private static class NumberArg implements Writable {
        private final Number value;

        NumberArg(Number value) {
            this.value = value;
        }

        @Override
        public void write(OutputStream os) throws IOException {
            os.write(value.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    private static class QuotedString implements Writable {
        final String value;

        QuotedString(String value) {
            this.value = value;
        }

        @Override
        public void write(OutputStream os) throws IOException {
            os.write('"');
            os.write(value.replace("\"", "\\\"").getBytes(StandardCharsets.UTF_8));
            os.write('"');
        }
    }

    private static class TestOperator implements Writable {
        final String name;
        final List<SieveArgument> arguments;

        TestOperator(String name, List<SieveArgument> arguments) {
            this.name = name;
            this.arguments = arguments;
        }

        @Override
        public void write(OutputStream os) throws IOException {
            os.write(name.getBytes(StandardCharsets.UTF_8));
            os.write('(');
            for (int j = 0; j < arguments.size(); j++) {
                if (j > 0) os.write(',');
                arguments.get(j).write(os);
            }
            os.write(')');
        }
    }

    private static class ConditionalBlock implements Writable {
        final String keyword;
        final SieveArgument condition;
        final List<SieveArgument> actions;

        ConditionalBlock(String keyword, SieveArgument condition, List<SieveArgument> actions) {
            if (actions == null || actions.isEmpty()) {
                throw new IllegalArgumentException(keyword + " block must have at least one action.");
            }
            this.keyword = keyword;
            this.condition = condition;
            this.actions = actions;
        }

        @Override
        public void write(OutputStream os) throws IOException {
            os.write(keyword.getBytes());
            if (condition != null) {
                os.write(' ');
                condition.write(os);
            }
            os.write(" {\n".getBytes());
            for (SieveArgument action : actions) {
                os.write("  ".getBytes());
                action.write(os);
                os.write(";\n".getBytes());
            }
            os.write("}\n".getBytes());
        }
    }

    private static class StringList implements Writable {
        final List<String> values;

        StringList(List<String> values) {
            this.values = values;
        }

        private void write(OutputStream os, String value) throws IOException {
            os.write('"');
            os.write(value.replace("\"", "\\\"").getBytes(StandardCharsets.UTF_8));
            os.write('"');
        }

        @Override
        public void write(OutputStream os) throws IOException {
            if (values.size() == 1) {
                write(os, values.get(0));
            } else {
                os.write('[');
                for (int i = 0; i < values.size(); i++) {
                    if (i > 0) os.write(',');
                    write(os, values.get(i));
                }
                os.write(']');
            }
        }
    }
}
