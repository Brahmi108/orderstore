package com.sweethill.orderstore.web.screens.goodnameoption;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.GoodNameOption;

@UiController("orderstore_GoodNameOption.edit")
@UiDescriptor("good-name-option-edit.xml")
@EditedEntityContainer("goodNameOptionDc")
@LoadDataBeforeShow
public class GoodNameOptionEdit extends StandardEditor<GoodNameOption> {
}