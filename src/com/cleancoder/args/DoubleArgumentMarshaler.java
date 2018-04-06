package com.cleancoder.args;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

import java.util.*;

public class DoubleArgumentMarshaler implements ArgumentMarshaler<Double> {
  private Double doubleValue = 0.0;

  @Override
  public void setValue(Optional<String> arg) throws ArgsException {
    if (!arg.isPresent()) {
      throw new ArgsException(MISSING_DOUBLE);
    }
    try {
      doubleValue = Double.parseDouble(arg.get());
    } catch (NumberFormatException e) {
      throw new ArgsException(INVALID_DOUBLE, arg.get());
    }
  }

  @Override
  public Double getValue() {
      return doubleValue;
  }

}
