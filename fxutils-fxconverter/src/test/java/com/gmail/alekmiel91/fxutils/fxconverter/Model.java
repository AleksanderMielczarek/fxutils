package com.gmail.alekmiel91.fxutils.fxconverter;

import com.gmail.alekmiel91.fxutils.fxconverter.annotation.FXConvert;
import com.gmail.alekmiel91.fxutils.fxconverter.types.Element;
import javafx.beans.property.*;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-27
 */
public class Model {

    @FXConvert(@FXConvert.Config(DataFirstNameSurName.class))
    private final StringProperty firstName = new SimpleStringProperty();

    @FXConvert(@FXConvert.Config(value = DataFirstNameSurName.class, getterSetterNames = "surname"))
    private final StringProperty surName = new SimpleStringProperty();

    @FXConvert
    private final StringProperty address = new SimpleStringProperty();

    @FXConvert(@FXConvert.Config(value = DataAge.class,
            getterSetterNames = {"age", "setNewAge"},
            getterSetterElements = {Element.FIELD, Element.METHOD},
            setterType = int.class))
    private final IntegerProperty age = new SimpleIntegerProperty();

    private final LongProperty id = new SimpleLongProperty();

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getSurName() {
        return surName.get();
    }

    public StringProperty surNameProperty() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName.set(surName);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }
}
