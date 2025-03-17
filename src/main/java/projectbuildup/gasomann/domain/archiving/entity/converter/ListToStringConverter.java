package projectbuildup.gasomann.domain.archiving.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Converter
public class ListToStringConverter implements AttributeConverter<List<String>, String> {
    private static final String DELIMITER = ", ";
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        return attribute.stream()
                .map(Objects::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER)).toList();
    }
}
