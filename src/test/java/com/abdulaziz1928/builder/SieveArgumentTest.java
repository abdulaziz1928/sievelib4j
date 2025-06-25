package com.abdulaziz1928.builder;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.List;

class SieveArgumentTest {

    @SneakyThrows
    private String writeToString(SieveArgument args) {
        var os = new ByteArrayOutputStream();
        args.write(os);
        return os.toString();
    }

    @Test
    void shouldWriteStringSuccessfully() {
        var args = new SieveArgument().writeString("hello");
        var actual = writeToString(args);
        Assertions.assertEquals("\"hello\"", actual);
    }

    @Test
    void shouldSanitizeStringSuccessfully() {
        var args = new SieveArgument().writeString("\\hello\"");
        var actual = writeToString(args);
        Assertions.assertEquals("\"\\\\hello\\\"\"", actual);
    }

    @Test
    void shouldFailWhenStringContainsNewLine() {
        var args = new SieveArgument().writeString("hello\r\n");
        Assertions.assertThrows(IllegalArgumentException.class, () -> writeToString(args));
    }

    @Test
    void shouldWriteAtomSuccessfully() {
        var args = new SieveArgument().writeAtom("hello");
        var actual = writeToString(args);
        Assertions.assertEquals("hello", actual);
    }

    @Test
    void shouldWriteLongNumberSuccessfully() {
        var args = new SieveArgument().writeNumber(1L);
        var actual = writeToString(args);
        Assertions.assertEquals("1", actual);
    }

    @Test
    void shouldWriteNumberSuccessfully() {
        var args = new SieveArgument().writeNumber(1);
        var actual = writeToString(args);
        Assertions.assertEquals("1", actual);
    }

    @Test
    void shouldWriteStringListSuccessfully() {
        var args = new SieveArgument().writeStringList(List.of("hello", "world"));
        var actual = writeToString(args);
        Assertions.assertEquals("[\"hello\",\"world\"]", actual);
    }

    @Test
    void shouldSanitizeStringListSuccessfully() {
        var args = new SieveArgument().writeStringList(List.of("\\hello\"", "world\""));
        var actual = writeToString(args);
        Assertions.assertEquals("[\"\\\\hello\\\"\",\"world\\\"\"]", actual);

    }

    @Test
    void shouldWriteSingularStringListAsStringSuccessfully() {
        var args = new SieveArgument().writeStringList(List.of("hello"));
        var actual = writeToString(args);
        Assertions.assertEquals("\"hello\"", actual);
    }

    @Test
    void shouldAppendSieveArgumentSuccessfully() {
        var args = new SieveArgument();
        var args2 = new SieveArgument().writeNumber(1);
        args.appendArgument(args2);
        var actual = writeToString(args);
        Assertions.assertEquals("1", actual);
    }

    @Test
    void shouldWriteTestSuccessfully() {
        var args = new SieveArgument()
                .writeTest("anyof",
                        List.of(new SieveArgument().writeAtom("true"),
                                new SieveArgument().writeAtom("false"))
                );
        var actual = writeToString(args);
        Assertions.assertEquals("anyof(true,false)", actual);
    }

    @Test
    void shouldWriteConditionalBlockSuccessfully() {
        var args = new SieveArgument()
                .writeConditionBlock("if", new SieveArgument().writeAtom("true"), List.of(new SieveArgument().writeAtom("keep")))
                .writeConditionBlock("else", null, List.of(new SieveArgument().writeAtom("discard")));
        var actual = writeToString(args);
        Assertions.assertEquals(
                "if true {\r\n  keep;\r\n} " +
                        "else {\r\n  discard;\r\n}", actual);
    }

    @Test
    void shouldWriteConditionalBlockWithoutConditionSuccessfully() {
        var args = new SieveArgument()
                .writeConditionBlock("else", null, List.of(new SieveArgument().writeAtom("keep")));
        var actual = writeToString(args);
        Assertions.assertEquals("else {\r\n  keep;\r\n}", actual);
    }

    @Test
    void shouldFailToWriteConditionalBlockWithoutActionsSuccessfully() {
        var args = new SieveArgument();
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> args.writeConditionBlock("if", new SieveArgument().writeAtom("true"), List.of()));
    }
}
