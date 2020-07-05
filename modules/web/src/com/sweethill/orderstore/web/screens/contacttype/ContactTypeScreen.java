package com.sweethill.orderstore.web.screens.contacttype;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.customer.ContactType;

import javax.inject.Inject;

@UiController("orderstore_ContactType.screen")
@UiDescriptor("contact-type-screen.xml")
@LookupComponent("ContactTypeDataGrid")
@LoadDataBeforeShow
public class ContactTypeScreen extends StandardLookup<ContactType> {
    @Inject
    private DataGrid<ContactType> ContactTypeDataGrid;
    @Inject
    private Notifications notifications;
    @Inject
    private CollectionContainer<ContactType> ContactTypeDc;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;
    private String execAction;

    @Subscribe("ContactTypeDataGrid.create")
    public void onContactTypeDataGridCreate(Action.ActionPerformedEvent event) {
        if (ContactTypeDataGrid.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("ContactTypeBrowseEditMessage"))
                    .show();
            return;
        }
        ContactType newContactType = metadata.create(ContactType.class);
        ContactType merged = getScreenData().getDataContext().merge(newContactType);
        ContactTypeDc.getMutableItems().add(merged);
        execAction = "create";
        ContactTypeDataGrid.edit(merged);
    }

    @Subscribe("ContactTypeDataGrid.edit")
    public void onContactTypeDataGridEdit(Action.ActionPerformedEvent event) {
        ContactType selected = ContactTypeDataGrid.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            ContactTypeDataGrid.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("ContactTypeBrowseSelectItem"))
                    .show();
        }
    }

    @Subscribe("ContactTypeDataGrid")
    public void onContactTypeDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        ContactType ContactType=(ContactType)event.getItem();
        getScreenData().getDataContext().commit();
        dataManager.commit(ContactType);
    }

    @Subscribe("ContactTypeDataGrid")
    public void onContactTypeDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        ContactType ContactType = (ContactType) event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            ContactTypeDc.getMutableItems().remove(ContactType);
            getScreenData().getDataContext().remove(ContactType);
        }
        execAction = null;
    }
}