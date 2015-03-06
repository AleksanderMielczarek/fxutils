package com.gmail.alekmiel91.fxutils.fxconverter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DefaultFXConverterFromModelToDataTest {

    public static final String FIRST_NAME = "Jan";
    public static final String SUR_NAME = "Kowalski";
    public static final String ADDRESS = "Pod mostem 20";
    public static final Integer AGE = 100;

    private final FXConverter FXConverter = FXConverters.getFxConverter();
    private final Model model = new Model();
    private final DataFirstNameSurName nameData = new DataFirstNameSurName();
    private final DataAge ageData = new DataAge();

    @Before
    public void setUp() throws Exception {
        model.setFirstName(FIRST_NAME);
        model.setSurName(SUR_NAME);
        model.setAddress(ADDRESS);
        model.setAge(AGE);
    }

    @Test
    public void testConvertFromModelToData() throws Exception {
        FXConverter.convertFromModelToData(model, nameData);
        FXConverter.convertFromModelToData(model, ageData);

        assertEquals(FIRST_NAME, nameData.getFirstName());
        assertEquals(SUR_NAME, nameData.getSurname());
        assertEquals(ADDRESS, nameData.getAddress());

        assertEquals(AGE, Integer.valueOf(ageData.getAge()));
        assertEquals(ADDRESS, ageData.getAddress());
    }

    @Test
    public void testConvertFromModelToDataWithInitialization() throws Exception {
        DataFirstNameSurName newNameData = FXConverter.convertFromModelToData(model, DataFirstNameSurName.class);
        DataAge newAgeData = FXConverter.convertFromModelToData(model, DataAge.class);

        assertEquals(FIRST_NAME, newNameData.getFirstName());
        assertEquals(SUR_NAME, newNameData.getSurname());
        assertEquals(ADDRESS, newNameData.getAddress());

        assertEquals(AGE, Integer.valueOf(newAgeData.getAge()));
        assertEquals(ADDRESS, newAgeData.getAddress());
    }
}