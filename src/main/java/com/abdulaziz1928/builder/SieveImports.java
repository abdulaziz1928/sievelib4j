package com.abdulaziz1928.builder;

public enum SieveImports {
    ;

    public static class Conditions {
        public static final String ENVELOPE = "envelope";
        public static final String BODY = "body";
        public static final String DATE = "date";
    }

    public static class Actions {
        public static final String FILE_INTO = "fileinto";
        public static final String COPY = "copy";
        public static final String VACATION = "vacation";
        public static final String IMAP4FLAGS = "imap4flags";
    }
}
