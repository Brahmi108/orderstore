package com.sweethill.orderstore.web.screens.contacttype;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.customer.ContactType;

@UiController("orderstore_ContactType.browse")
@UiDescriptor("contact-type-browse.xml")
@LookupComponent("contactTypesTable")
@LoadDataBeforeShow
public class ContactTypeBrowse extends StandardLookup<ContactType> {
}