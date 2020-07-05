package com.sweethill.orderstore.web.screens.addresstypes;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.customer.AddressTypes;


import javax.inject.Inject;

@UiController("orderstore_AddressTypes.screen")
@UiDescriptor("address-types-screen.xml")
@LookupComponent("AddressTypesDataGrid")
@LoadDataBeforeShow
public class AddressTypesScreen extends Screen {
    @Inject
    private DataGrid<AddressTypes> AddressTypesDataGrid;
    @Inject
    private Notifications notifications;
    @Inject
    private CollectionContainer<AddressTypes> AddressTypesDc;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;
    private String execAction;
    @Inject
    private CollectionLoader<AddressTypes> AddressTypesDl;

    @Subscribe("AddressTypesDataGrid.create")
    public void onAddressTypesDataGridCreate(Action.ActionPerformedEvent event) {
        if (AddressTypesDataGrid.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("AddressTypesBrowseEditMessage"))
                    .show();
            return;
        }
        AddressTypes newAddressTypes = metadata.create(AddressTypes.class);
        AddressTypes merged = getScreenData().getDataContext().merge(newAddressTypes);
        AddressTypesDc.getMutableItems().add(merged);
        execAction = "create";
        AddressTypesDataGrid.edit(merged);
    }

    @Subscribe("AddressTypesDataGrid.edit")
    public void onAddressTypesDataGridEdit(Action.ActionPerformedEvent event) {
        AddressTypes selected = AddressTypesDataGrid.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            AddressTypesDataGrid.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("AddressTypesBrowseSelectItem"))
                    .show();
        }
    }

    @Subscribe("AddressTypesDataGrid")
    public void onAddressTypesDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        AddressTypes AddressTypes=(AddressTypes)event.getItem();
        getScreenData().getDataContext().commit();
        dataManager.commit(AddressTypes);
    }

    @Subscribe("AddressTypesDataGrid")
    public void onAddressTypesDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        AddressTypes AddressTypes = (AddressTypes) event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            AddressTypesDc.getMutableItems().remove(AddressTypes);
            getScreenData().getDataContext().remove(AddressTypes);
        }
        execAction = null;
    }
}