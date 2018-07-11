package com.autokartz.autokartz.interfaces;

import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;

/**
 * Created by Cortana on 1/16/2018.
 */

public interface GetCarPartsListener {

    void getSelectedCarPart(CategoryInformation categoryInformation);

    void getUnSelectedCarPart(CategoryInformation categoryInformation);

}