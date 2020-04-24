package com.sweethill.orderstore.web.screens.costtype;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.gui.Notifications;
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
    private String execAction;
    @Inject
    private CollectionLoader<CostType> costTypeDl;

    @Subscribe
    public void onInit(InitEvent event) {
        owner = orderStoreService.getCurrentUserOwner();
        costTypeDl.setParameter("owner", owner);
    }

    @Subscribe("costTypeDataGrid.create")
    public void onCostTypeDataGridCreate(Action.ActionPerformedEvent event) {
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
        execAction = "create";
        costTypeDataGrid.edit(merged);
    }

    @Subscribe("costTypeDataGrid.edit")
    public void onCostTypeDataGridEdit(Action.ActionPerformedEvent event) {
        CostType selected = costTypeDataGrid.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            costTypeDataGrid.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("costTypeBrowseSelectItem"))
                    .show();
        }
    }

    @Subscribe("costTypeDataGrid")
    public void oncostTypeDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        CostType costType=(CostType)event.getItem();
        getScreenData().getDataContext().commit();
        dataManager.commit(costType);
        if ( costType.getDef() )
        {
            orderStoreService.setDefaultCostType(costType);
            getScreenData().loadAll();
        }
    }

    @Subscribe("costTypeDataGrid")
    public void onCostTypeDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        CostType costType = (CostType) event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            costTypeDc.getMutableItems().remove(costType);
            getScreenData().getDataContext().remove(costType);
        }
        execAction = null;
    }
}