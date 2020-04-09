package com.sweethill.orderstore.web.screens.units;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Actions;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.Units;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.List;

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
    private Actions actions;
    @Inject
    private DataGrid<Units> unitsDataGrid;
    @Inject
    private Notifications notifications;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionContainer<Units> unitsDc;
    @Subscribe
    public void onInit(InitEvent event) {
        owner = orderStoreService.getCurrentUserOwner();
        CreateAction<Units> createAction = (CreateAction<Units>) actions.create(CreateAction.ID);
        createAction.withHandler(actionPerformedEvent -> {
            if (unitsDataGrid.isEditorActive()) {
                notifications.create()
                        .withCaption("Close the editor before creating a new entity")
                        .show();
                return;
            }
            Units newUnits = metadata.create(Units.class);
            Units merged = getScreenData().getDataContext().merge(newUnits);
            merged.setOwner(owner);
            unitsDc.getMutableItems().add(merged);
            unitsDataGrid.edit(merged);
        });
        unitsDataGrid.addAction(createAction);

        EditAction<Units> editAction = (EditAction<Units>) actions.create(EditAction.ID);
        editAction.withHandler(actionPerformedEvent -> {
            Units selected = unitsDataGrid.getSingleSelected();
            if (selected != null) {
                unitsDataGrid.edit(selected);
            } else {
                notifications.create()
                        .withCaption("Item is not selected")
                        .show();
            }
        });
        unitsDataGrid.addAction(editAction);
    }

    @Subscribe("unitsDataGrid")
    public void onUnitsDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
        getScreenData().loadAll();
    }

    @Install(to = "unitsDl", target = Target.DATA_LOADER)
    private List<Units> unitsDlLoadDelegate(LoadContext<Units> loadContext) {
        List<Units> list;
        Owner owner=orderStoreService.getCurrentUserOwner();
        if (owner != null) {
            loadContext.setQueryString("select e from orderstore_Units e where e.owner = :owner");
            LoadContext.Query query = loadContext.getQuery();
            query.setParameter("owner", owner);
        }
        list = dataManager.loadList(loadContext);
        return list;
    }

}