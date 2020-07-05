package com.sweethill.orderstore.web.screens.customer;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.customer.Address;
import com.sweethill.orderstore.entity.customer.Contact;
import com.sweethill.orderstore.entity.customer.Customer;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
@LoadDataBeforeShow
public class CustomerEdit extends StandardEditor<Customer> {
    private String execAction;
    @Inject
    private DataGrid<Address> addresses_Table;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<Address> addressesDC;
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private DataGrid<Contact> contacts_Table;
    @Inject
    private CollectionPropertyContainer<Contact> contactsDC;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Customer> event) {
        event.getEntity().setOwner(orderStoreService.getCurrentUserOwner());
    }

    @Subscribe("addresses_Table.create")
    public void onAddresses_TableCreate(Action.ActionPerformedEvent event) {
        if (addresses_Table.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("addresses_TableEditMessage"))
                    .show();
            return;
        }
        Address address = metadata.create(Address.class);
        Address merged = getScreenData().getDataContext().merge(address);
        addressesDC.getMutableItems().add(merged);
        execAction = "create";
        addresses_Table.edit(merged);
    }

    @Subscribe("addresses_Table.edit")
    public void onAddresses_TableEdit(Action.ActionPerformedEvent event) {
        Address selected = addresses_Table.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            addresses_Table.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("addresses_SelectMessage"))
                    .show();
        }
    }

    @Subscribe("addresses_Table")
    public void onAddresses_TableEditorPreCommit(DataGrid.EditorPreCommitEvent<? extends Address> event) {
        Customer customer = getEditedEntity();
        Address address = event.getItem();
        address.setCustomer(customer);
    }

    @Subscribe("addresses_Table")
    public void onAddresses_TableEditorClose(DataGrid.EditorCloseEvent<? extends Address> event) {
        Address address = event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            addressesDC.getMutableItems().remove(address);
            getScreenData().getDataContext().remove(address);
        }
        execAction = null;
    }

    @Subscribe("contacts_Table.create")
    public void oncontacts_TableCreate(Action.ActionPerformedEvent event) {
        if (contacts_Table.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("contacts_TableEditMessage"))
                    .show();
            return;
        }
        Contact contact = metadata.create(Contact.class);
        Contact merged = getScreenData().getDataContext().merge(contact);
        contactsDC.getMutableItems().add(merged);
        execAction = "create";
        contacts_Table.edit(merged);
    }

    @Subscribe("contacts_Table.edit")
    public void oncontacts_TableEdit(Action.ActionPerformedEvent event) {
        Contact selected = contacts_Table.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            contacts_Table.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("contacts_SelectMessage"))
                    .show();
        }
    }

    @Subscribe("contacts_Table")
    public void oncontacts_TableEditorPreCommit(DataGrid.EditorPreCommitEvent<? extends Contact> event) {
        Customer customer = getEditedEntity();
        Contact contact = event.getItem();
        contact.setCustomer(customer);
    }

    @Subscribe("contacts_Table")
    public void oncontacts_TableEditorClose(DataGrid.EditorCloseEvent<? extends Contact> event) {
        Contact contact = event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            contactsDC.getMutableItems().remove(contact);
            getScreenData().getDataContext().remove(contact);
        }
        execAction = null;
    }
}