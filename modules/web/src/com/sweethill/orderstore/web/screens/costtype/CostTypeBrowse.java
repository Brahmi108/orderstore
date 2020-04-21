package com.sweethill.orderstore.web.screens.costtype;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.CostType;

@UiController("orderstore_CostType.browse")
@UiDescriptor("cost-type-browse.xml")
@LookupComponent("costTypesTable")
@LoadDataBeforeShow
public class CostTypeBrowse extends StandardLookup<CostType> {
}