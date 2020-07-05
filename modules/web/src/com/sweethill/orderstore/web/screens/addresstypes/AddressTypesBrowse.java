package com.sweethill.orderstore.web.screens.addresstypes;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.customer.AddressTypes;

@UiController("orderstore_AddressTypes.browse")
@UiDescriptor("address-types-browse.xml")
@LookupComponent("addressTypesesTable")
@LoadDataBeforeShow
public class AddressTypesBrowse extends StandardLookup<AddressTypes> {
}