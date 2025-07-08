package com.abdulaziz1928.builder;


public final class ForEveryPartBlock extends SieveEnclosingBlock {
    private static final String BLOCK_NAME = "foreverypart";

    public ForEveryPartBlock() {
        applyImport(BLOCK_NAME);
    }

    @Override
    SieveArgument getBlockValue() {
        return new SieveArgument().writeAtom(BLOCK_NAME);
    }
}
