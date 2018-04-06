package com.cleancoder.args;

import java.util.Optional;

/**
 * This is the interface for marshalers. Marshaler defines a
 * type of parameter (Integer, Boolean etc.). It also exposes
 * the tail that clients can use to specify this type in the schema.
 *
 * Please make sure that new marshalers are added to {@link Marshalers}
 * class.
 *
 * @param <T> Type of value that can be stored by the marshaler.
 */
public interface ArgumentMarshaler<T> {
  /**
   * Sets value for the marshaler
   * @param arg argument to be set
   * @throws ArgsException if set fails.
   */
  void setValue(final Optional<String> arg) throws ArgsException;

  /**
   * Returns the value for the param
   * @return Value of type T
   */
  T getValue();

  /**
   * This returns true if param has a value attached. Since
   * most of the types (Integer, Strings etc.) require values,
   * we default it to true.
   * @return true if param has value otherwise false
   */
  default boolean needsValue() { return true; }
}
