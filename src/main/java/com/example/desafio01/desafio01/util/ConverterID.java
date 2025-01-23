package com.example.desafio01.desafio01.util;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class ConverterID {
    public static UUID convertStringToUUID(String id) {
        String formattedUUIDString = id.toString().replaceFirst(
            "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
        );
        return UUID.fromString(formattedUUIDString);
    }
}
