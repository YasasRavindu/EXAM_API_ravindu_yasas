package com.emapta.examapi.modelmapper.converter;


import com.emapta.examapi.enums.DateTimePattern;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;


@Component
public class LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {

    @Override
    public String convert(MappingContext<LocalDateTime, String> context) {
        if (context.getSource() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateTimePattern.STRICT_DATE_TIME.getPattern())
                    .withResolverStyle(ResolverStyle.STRICT);
            return formatter.format(context.getSource());
        } else {
            return null;
        }
    }

}
