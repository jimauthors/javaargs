package com.cleancoder.args;

import java.util.Optional;

public class ColorsArgumentMarshaler implements ArgumentMarshaler<Colors> {
    private Colors color;

    @Override
    public void setValue(Optional<String> arg) throws ArgsException {
        if(!arg.isPresent()) {
            throw new ArgsException(ArgsException.ErrorCode.MISSING_VALUE);
        }
        color = Colors.valueOf(arg.get());
    }

    @Override
    public Colors getValue() {
        return color;
    }
}
