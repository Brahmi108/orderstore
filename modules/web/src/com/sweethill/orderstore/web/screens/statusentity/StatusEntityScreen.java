package com.sweethill.orderstore.web.screens.statusentity;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.StatusEntity;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_StatusEntity.screen")
@UiDescriptor("status-entity-screen.xml")
@LookupComponent("statusEntitiesTable")
@LoadDataBeforeShow
public class StatusEntityScreen extends StandardLookup<StatusEntity> {
    private String execAction;
    @Inject
    private OrderStoreService orderStoreService;
    private Owner owner;
    @Inject
    private DataManager dataManager;
    @Inject
    private CollectionLoader<StatusEntity> statusEntitiesDl;
    @Inject
    private CollectionContainer<StatusEntity> statusEntitiesDc;
    @Inject
    private DataGrid<StatusEntity> statusEntitiesTable;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Metadata metadata;

    @Subscribe
    public void onInit(InitEvent event) {
        owner = orderStoreService.getCurrentUserOwner();
        statusEntitiesDl.setParameter("owner", owner);
    }

    @Subscribe("statusEntitiesTable.create")
    public void onStatusEntitiesTableCreate(Action.ActionPerformedEvent event) {
        if (statusEntitiesTable.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("StatusEntityBrowseEditMessage"))
                    .show();
            return;
        }
        StatusEntity statusEntity = metadata.create(StatusEntity.class);
        StatusEntity merged = getScreenData().getDataContext().merge(statusEntity);
        merged.setOwner(owner);
        statusEntitiesDc.getMutableItems().add(merged);
        execAction = "create";
        statusEntitiesTable.edit(merged);
    }

    @Subscribe("statusEntitiesTable.edit")
    public void onStatusEntitiesTableEdit(Action.ActionPerformedEvent event) {
        StatusEntity selected = statusEntitiesTable.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            statusEntitiesTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("StatusEntityBrowseSelectItem"))
                    .show();
        }
    }

    @Subscribe("statusEntitiesTable")
    public void onStatusEntitiesTableEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        StatusEntity statusEntity=(StatusEntity) event.getItem();
        getScreenData().getDataContext().commit();
        dataManager.commit(statusEntity);
    }

    @Subscribe("statusEntitiesTable")
    public void onStatusEntitiesTableEditorClose(DataGrid.EditorCloseEvent event) {
        StatusEntity statusEntity = (StatusEntity) event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if ( execAction.equals("create") ) {
            statusEntitiesDc.getMutableItems().remove(statusEntity);
            getScreenData().getDataContext().remove(statusEntity);
        }
        execAction = null;
    }
}