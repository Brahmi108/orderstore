package com.sweethill.orderstore.web.screens.statusentitytypes;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.StatusEntityTypes;

@UiController("orderstore_StatusEntityTypes.browse")
@UiDescriptor("status-entity-types-browse.xml")
@LookupComponent("statusEntityTypesesTable")
@LoadDataBeforeShow
public class StatusEntityTypesBrowse extends StandardLookup<StatusEntityTypes> {
}