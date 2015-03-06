package com.gmail.alekmiel91.fxutils.fxconverter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultFXConverterFromDataToModelTest {

    public static final String FIRST_NAME = "Jan";
    public static final String SUR_NAME = "Kowalski";
    public static final String ADDRESS = "Pod mostem 20";
    public static final Integer AGE = 100;

    private final FXConverter FXConverter = FXConverters.getFxConverter();
    private final Model model = new Model();
    private final DataFirstNameSurName nameData = new DataFirstNameSurName();
    private final DataAge ageData = new DataAge();

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Before
    public void setUp() throws Exception {
        nameData.setFirstName(FIRST_NAME);
        nameData.setSurname(SUR_NAME);
        nameData.setAddress(ADDRESS);

        ageData.setNewAge(AGE);
        ageData.setAddress(ADDRESS);
    }

    @Test
    public void testConvertFromDataToModel() throws Exception {
        FXConverter.convertFromDataToModel(nameData, model);
        FXConverter.convertFromDataToModel(ageData, model);

        assertEquals(FIRST_NAME, model.getFirstName());
        assertEquals(SUR_NAME, model.getSurName());
        assertEquals(ADDRESS, model.getAddress());

        assertEquals(AGE, Integer.valueOf(model.getAge()));
    }

    @Test
    public void testConvertFromDataToModelWithInitialization() throws Exception {
        Model newModel = FXConverter.convertFromDataToModel(nameData, Model.class);

        assertEquals(FIRST_NAME, newModel.getFirstName());
        assertEquals(SUR_NAME, newModel.getSurName());
        assertEquals(ADDRESS, newModel.getAddress());
    }

    @Test
    public void testConvertFromDataToModelWithInitialization2() throws Exception {
        Model newModel = FXConverter.convertFromDataToModel(ageData, Model.class);

        assertEquals(AGE, Integer.valueOf(newModel.getAge()));
        assertEquals(ADDRESS, newModel.getAddress());
    }
}