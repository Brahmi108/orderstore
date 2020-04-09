package com.sweethill.orderstore.web.screens.owner;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;

@UiController("orderstore_Owner.browse")
@UiDescriptor("owner-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class OwnerBrowse extends MasterDetailScreen<Owner> {
}