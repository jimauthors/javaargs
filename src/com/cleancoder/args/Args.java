package com.cleancoder.args;

import lombok.NonNull;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.*;

/**
 * This class is used to parse commandline arguments according
 * to a user defined schema.
 */
public class Args {
  private final Map<Character, String> idToTail = new HashMap<>();
  private final MarshalerFactory marshalersFactory = new MarshalerFactory();
  private final Set<Character> argsFound = new HashSet<>();
  private static final String PARAM_PREFIX = "-";
  private static final String SCHEMA_SEPARATOR = ",";
  private ListIterator<String> currentArgument;

  /**
   * Constructor
   * @param schema Comma-separated schema where each element has "id+tail"
   *               format. "id" is the command-line parameter and "tail" is
   *               used for determining the type of value.
   * @param args List of actual command line params to be processed.
   * @throws ArgsException when schema as well as args processing fails.
   */
  public Args(@NonNull final String schema, @NonNull final String[] args) {
    parseSchema(schema);
    parseArgumentStrings(Arrays.asList(args));
  }

  /* Schema parsing helpers */
  private void parseSchema(final String schema) {
    for (final String element : schema.split(SCHEMA_SEPARATOR))
      if (element.trim().length() > 0)
        parseSchemaElement(element.trim());
  }

  private void parseSchemaElement(final String element) {
    final char elementId = element.charAt(0);
    final String elementTail = element.substring(1);
    validateSchemaElement(elementId, elementTail);
    idToTail.put(elementId, elementTail);
  }

  private void validateSchemaElement(final char elementId, final String elementTail) {
    if (!Character.isLetter(elementId))
      throw new ArgsException(INVALID_ARGUMENT_NAME, elementId, null);
    if (!marshalersFactory.hasMarshaler(elementTail))
      throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
  }

  /* Argument parsing helper */
  private void parseArgumentStrings(final List<String> argsList) {
    for (currentArgument = argsList.listIterator(); currentArgument.hasNext();) {
      final String argString = currentArgument.next();
      if (!argString.startsWith(PARAM_PREFIX)) {
        currentArgument.previous();
        break;
      }
      parseArgumentCharacters(argString.substring(PARAM_PREFIX.length()));
    }
  }

  private void parseArgumentCharacters(final String argChars) {
    for (int i = 0; i < argChars.length(); i++)
      parseArgumentCharacter(argChars.charAt(i));
  }

  private void parseArgumentCharacter(final char argChar) {
    if (!idToTail.containsKey(argChar)) {
      throw new ArgsException(UNEXPECTED_ARGUMENT, argChar, null);
    }
    argsFound.add(argChar);
    final String elementTail = idToTail.get(argChar);
    final ArgumentMarshaler marshaler = marshalersFactory.getMarshaler(elementTail, argChar);
    final Optional<String> argValue = getArgValue(marshaler);
    try {
      marshaler.setValue(argValue);
    } catch (ArgsException e) {
      e.setErrorArgumentId(argChar);
      throw e;
    }
  }

  private Optional<String> getArgValue(final ArgumentMarshaler argMarshaler) {
    Optional<String> value = Optional.empty();
    if (argMarshaler.needsValue()) {
      try {
        value = Optional.of(currentArgument.next());
      } catch(NoSuchElementException e) {
        return value;
      }
    }
    return value;
  }

  /**
   * Returns the value for a given command line parameter
   * @param elementId parameter id
   * @return value object
   */
  public Object getValue(final char elementId) {
    final String elementTail = idToTail.get(elementId);
    final ArgumentMarshaler marshaler = marshalersFactory.getMarshaler(elementTail, elementId);
    return marshaler.getValue();
  }

  public boolean has(final char arg) {
    return argsFound.contains(arg);
  }

  public int nextArgument() {
    return currentArgument.nextIndex();
  }
}