package com.sweethill.orderstore.web.screens.customer;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.customer.Customer;

@UiController("orderstore_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
@LoadDataBeforeShow
public class CustomerEdit extends StandardEditor<Customer> {
}