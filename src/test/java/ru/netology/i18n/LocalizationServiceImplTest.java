package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationServiceImplTest {

    @Test
    void localeShouldSendRussia() {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String actual = localizationService.locale(Country.RUSSIA);
        Assertions.assertEquals("Добро пожаловать", actual);
    }

    @ParameterizedTest
    @MethodSource("testSource")
    void localeShouldSendOther(Country country) {
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String actual = localizationService.locale(country);
        Assertions.assertEquals("Welcome", actual);
    }

    private static Stream<Arguments> testSource() {
        return Stream.of(
                Arguments.of(Country.USA),
                Arguments.of(Country.BRAZIL),
                Arguments.of(Country.GERMANY));
    }

}