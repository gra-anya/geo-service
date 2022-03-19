package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

public class GeoServiceImplTest {

    @Test
    public void byIpShouldReturnRightLocation(){
        GeoServiceImpl geoService = new GeoServiceImpl();

        Location actual = geoService.byIp("172.0.32.11");
        Location expected = new Location("Moscow", Country.RUSSIA, "Lenina", 15);

        Assertions.assertEquals(expected, actual);
    }

}
