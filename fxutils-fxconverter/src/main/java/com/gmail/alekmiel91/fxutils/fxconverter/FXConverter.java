package com.gmail.alekmiel91.fxutils.fxconverter;

/**
 * @author Aleksander Mielczarek
 * @since 2015-02-26
 */
public interface FXConverter {
    public <T, S> void convertFromModelToData(T fromModel, S toData);

    public <T, S> S convertFromModelToData(T fromModel, Class<S> toDataClass);

    public <T, S> void convertFromDataToModel(T fromData, S toModel);

    public <T, S> S convertFromDataToModel(T fromData, Class<S> toModelClass);
}
