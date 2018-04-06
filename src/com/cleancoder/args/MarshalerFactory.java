package com.cleancoder.args;

import lombok.NonNull;

import java.util.*;

import static com.cleancoder.args.ArgsException.ErrorCode.INVALID_ARGUMENT_FORMAT;

/**
 * Factory for marshalers. New marshalers should be added in the marshalerMap.
 */
public class MarshalerFactory {
    private final Map<Character, ArgumentMarshaler> marshalers = new HashMap<>();
    private static Map<String, Class<? extends ArgumentMarshaler>> marshalerMap =
            new HashMap<String, Class<? extends ArgumentMarshaler>>() {{
            put("",     BooleanArgumentMarshaler.class);
            put("*",    StringArgumentMarshaler.class);
            put("#",    IntegerArgumentMarshaler.class);
            put("##",   DoubleArgumentMarshaler.class);
            put("[*]",  StringArrayArgumentMarshaler.class);
            put("&",    MapArgumentMarshaler.class);
            put("$",    ColorsArgumentMarshaler.class);
        }};

    /**
     * Checks if marshaler is available for an elementTail
     * @param elementTail element tail
     * @return true if marshaler exists
     */
    public boolean hasMarshaler(final String elementTail) {
        return marshalerMap.containsKey(elementTail);
    }

    /**
     * This is the method that instantiates a marshaler for a given tail.
     * It implements the Multiton pattern where a marshaler is instantiate only
     * once for a given elementId.
     * @param elementTail element tail used for instantiation
     * @param elementId element id used for multiton behavior
     * @return marshaler
     * @throws ArgsException if marshaler cannot be instantiated
     */
    public ArgumentMarshaler getMarshaler(@NonNull final String elementTail, final char elementId) {
        if (!marshalerMap.containsKey(elementTail)) {
            throw new ArgsException(INVALID_ARGUMENT_FORMAT, elementId, elementTail);
        }
        if(marshalers.containsKey(elementId)) {
            return marshalers.get(elementId);
        }
        final Class marshellerClass = marshalerMap.get(elementTail);
        try {
            ArgumentMarshaler marshaler = (ArgumentMarshaler)marshellerClass.newInstance();
            marshalers.put(elementId, marshaler);
            return marshaler;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Should never happen!");
        }
    }
}
