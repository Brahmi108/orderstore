package com.sweethill.orderstore.web.screens.units;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.Units;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_UnitsBrowse")
@UiDescriptor("units-browse.xml")
@LoadDataBeforeShow
public class UnitsBrowse extends Screen {
    @Inject
    private OrderStoreService orderStoreService;
    private Owner owner;
    @Inject
    private DataManager dataManager;
    @Inject
    private DataGrid<Units> unitsDataGrid;
    @Inject
    private Notifications notifications;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionContainer<Units> unitsDc;
    @Inject
    private MessageBundle messageBundle;
    private String execAction;
    @Inject
    private CollectionLoader<Units> unitsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        owner = orderStoreService.getCurrentUserOwner();
        unitsDl.setParameter("owner", owner);
    }

    @Subscribe("unitsDataGrid.create")
    public void onUnitsDataGridCreate(Action.ActionPerformedEvent event) {
        if (unitsDataGrid.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("UnitsBrowseEditMessage"))
                    .show();
            return;
        }
        Units newUnits = metadata.create(Units.class);
        Units merged = getScreenData().getDataContext().merge(newUnits);
        merged.setOwner(owner);
        unitsDc.getMutableItems().add(merged);
        execAction = "create";
        unitsDataGrid.edit(merged);
    }

    @Subscribe("unitsDataGrid.edit")
    public void onUnitsDataGridEdit(Action.ActionPerformedEvent event) {
        Units selected = unitsDataGrid.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            unitsDataGrid.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("UnitsBrowseSelectItem"))
                    .show();
        }
    }

    @Subscribe("unitsDataGrid")
    public void onUnitsDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        Units units=(Units)event.getItem();
        getScreenData().getDataContext().commit();
        dataManager.commit(units);
    }

    @Subscribe("unitsDataGrid")
    public void onUnitsDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        Units unit = (Units) event.getItem();
        if ( execAction.equals("create") ) {
            unitsDc.getMutableItems().remove(unit);
            getScreenData().getDataContext().remove(unit);
        }
    }
}