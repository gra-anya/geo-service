package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderTest {

    @Test
    public void senderShouldSendRightLanguageWithRus() {
        String ipAddress = "172.0.32.11";
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ipAddress))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<>();
        header.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddress);

        String expected = "Добро пожаловать";
        String actual = messageSender.send(header);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void senderShouldSendRightLanguageAnother() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("96.44.183.149"))
                .thenReturn(new Location(null, null, null, 0));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Mockito.any()))
                .thenReturn("Welcome");

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> header = new HashMap<>();
        header.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        String expected = "Welcome";
        String actual = messageSender.send(header);

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvSource({"'96.44.183.149','Welcome'"
            , "'96.0.32.11', 'Welcome'"
            , "'172.0.32.11', 'Добро пожаловать'"})
    public void senderShouldSendRightLanguage(String ip, String hello) {
        Map<String, String> header = new HashMap<>();
        header.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        GeoService geoService = new GeoServiceImpl();
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);

        String actual = messageSender.send(header);

        Assertions.assertEquals(hello, actual);
    }

}
