package com.sweethill.orderstore.web.screens.goodsgroups;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.Actions;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.GoodsGroups;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.List;

@UiController("orderstore_GoodsgroupsScreen")
@UiDescriptor("goodsGroups-screen.xml")
@LoadDataBeforeShow
public class GoodsgroupsScreen extends Screen {
    @Inject
    private OrderStoreService orderStoreService;
    private Owner owner;
    @Inject
    private DataManager dataManager;
    @Inject
    private DataGrid<GoodsGroups> goodsGroupsDataGrid;
    @Inject
    private Notifications notifications;
    @Inject
    private Actions actions;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionContainer<GoodsGroups> goodsGroupsDc;

    @Subscribe
    public void onInit(InitEvent event) {
        owner = orderStoreService.getCurrentUserOwner();
        CreateAction createAction = (CreateAction) actions.create(CreateAction.ID);
        createAction.withHandler(actionPerformedEvent -> {
            if (goodsGroupsDataGrid.isEditorActive()) {
                notifications.create()
                        .withCaption(messageBundle.formatMessage("GoodsGroupsBrowseEditMessage"))
                        .show();
                return;
            }
            GoodsGroups newGoodsGroups = metadata.create(GoodsGroups.class);
            GoodsGroups merged = getScreenData().getDataContext().merge(newGoodsGroups);
            merged.setOwner(owner);
            goodsGroupsDc.getMutableItems().add(merged);
            goodsGroupsDataGrid.edit(merged);
        });
        goodsGroupsDataGrid.addAction(createAction);

        EditAction editAction = (EditAction) actions.create(EditAction.ID);
        editAction.withHandler(actionPerformedEvent -> {
            GoodsGroups selected = goodsGroupsDataGrid.getSingleSelected();
            if (selected != null) {
                goodsGroupsDataGrid.edit(selected);
            } else {
                notifications.create()
                        .withCaption(messageBundle.formatMessage("GoodsGroupsBrowseSelectItem"))
                        .show();
            }
        });
        goodsGroupsDataGrid.addAction(editAction);
    }

    @Subscribe("goodsGroupsDataGrid")
    public void onGoodsGroupsDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        getScreenData().getDataContext().commit();
        getScreenData().loadAll();
    }

    @Install(to = "goodsGroupsDl", target = Target.DATA_LOADER)
    private List<GoodsGroups> goodsGroupsDlLoadDelegate(LoadContext<GoodsGroups> loadContext) {
        List<GoodsGroups> list;
        if (owner != null) {
            loadContext.setQueryString("select e from orderstore_GoodsGroups e where e.owner = :owner");
            LoadContext.Query query = loadContext.getQuery();
            query.setParameter("owner", owner);
        }
        list = dataManager.loadList(loadContext);
        return list;
    }
}