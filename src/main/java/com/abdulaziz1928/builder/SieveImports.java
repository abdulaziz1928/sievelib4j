package com.abdulaziz1928.builder;

public enum SieveImports {
    ;

    public static class Common {
        public static final String VARIABLES = "variables";
        public static final String IMAP4FLAGS = "imap4flags";
    }

    public static class Conditions {
        public static final String ENVELOPE = "envelope";
        public static final String BODY = "body";
        public static final String DATE = "date";
        public static final String RELATIONAL = "relational";
        public static final String INDEX = "index";
        public static final String REGEX = "regex";
    }

    public static class Actions {
        public static final String FILE_INTO = "fileinto";
        public static final String COPY = "copy";
        public static final String VACATION = "vacation";
    }
}
