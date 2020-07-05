package com.sweethill.orderstore.web.screens.customer;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.customer.Customer;

@UiController("orderstore_Customer.screen")
@UiDescriptor("customer-screen.xml")
@LookupComponent("customersTable")
@LoadDataBeforeShow
public class CustomerScreen extends StandardLookup<Customer> {
}