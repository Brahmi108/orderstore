package com.sweethill.orderstore.web.screens.costtype;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.Actions;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.CostType;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.List;

@UiController("orderstore_CostTypeScreen")
@UiDescriptor("CostType-screen.xml")
@LoadDataBeforeShow
public class CostTypeScreen extends Screen {
    @Inject
    private OrderStoreService orderStoreService;
    private Owner owner;
    @Inject
    private Actions actions;
    @Inject
    private DataGrid<CostType> costTypeDataGrid;
    @Inject
    private Notifications notifications;
    @Inject
    private CollectionContainer<CostType> costTypeDc;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    @Subscribe
    public void onInit(InitEvent event) {
        owner = orderStoreService.getCurrentUserOwner();
        CreateAction createAction = (CreateAction) actions.create(CreateAction.ID);
        createAction.withHandler(actionPerformedEvent -> {
            if (costTypeDataGrid.isEditorActive()) {
                notifications.create()
                        .withCaption(messageBundle.formatMessage("costTypeBrowseEditMessage"))
                        .show();
                return;
            }
            CostType newcostType = metadata.create(CostType.class);
            CostType merged = getScreenData().getDataContext().merge(newcostType);
            merged.setOwner(owner);
            costTypeDc.getMutableItems().add(merged);
            costTypeDataGrid.edit(merged);
        });
        costTypeDataGrid.addAction(createAction);

        EditAction editAction = (EditAction) actions.create(EditAction.ID);
        editAction.withHandler(actionPerformedEvent -> {
            CostType selected = costTypeDataGrid.getSingleSelected();
            if (selected != null) {
                costTypeDataGrid.edit(selected);
            } else {
                notifications.create()
                        .withCaption(messageBundle.formatMessage("costTypeBrowseSelectItem"))
                        .show();
            }
        });
        costTypeDataGrid.addAction(editAction);
    }

    @Subscribe("costTypeDataGrid")
    public void oncostTypeDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        orderStoreService.setDefaultCostType((CostType)event.getItem());
        getScreenData().getDataContext().commit();
        getScreenData().loadAll();
    }

    @Install(to = "costTypeDl", target = Target.DATA_LOADER)
    private List<CostType> costTypeDlLoadDelegate(LoadContext<CostType> loadContext) {
        List<CostType> list;
        if (owner != null) {
            loadContext.setQueryString("select e from orderstore_CostType e where e.owner = :owner");
            LoadContext.Query query = loadContext.getQuery();
            query.setParameter("owner", owner);
        }
        list = dataManager.loadList(loadContext);
        return list;
    }
}