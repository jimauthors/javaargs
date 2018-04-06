package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class IntegerArgumentMarshaler implements ArgumentMarshaler<Integer> {
  private Integer intValue = 0;

  @Override
  public void setValue(final Optional<String> arg) throws ArgsException {
    if (!arg.isPresent()) {
      throw new ArgsException(MISSING_INTEGER);
    }
    try {
      intValue = Integer.parseInt(arg.get());
    } catch (NumberFormatException e) {
      throw new ArgsException(INVALID_INTEGER, arg.get());
    }
  }

  @Override
  public Integer getValue() {
      return intValue;
  }

}
