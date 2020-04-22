package com.sweethill.orderstore.web.screens.stock;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.Stock;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.List;

@UiController("orderstore_StockScreen")
@UiDescriptor("stock-screen.xml")
@LoadDataBeforeShow
public class StockScreen extends Screen {
    @Inject
    private OrderStoreService orderStoreService;
    private Owner owner;
    @Inject
    private DataManager dataManager;
    @Inject
    private DataGrid<Stock> stocksDataGrid;
    @Inject
    private Notifications notifications;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionContainer<Stock> stocksDc;
    @Inject
    private MessageBundle messageBundle;
    private String execAction;

    @Subscribe
    public void onInit(InitEvent event) {
        owner = orderStoreService.getCurrentUserOwner();
    }

    @Subscribe("stocksDataGrid.create")
    public void onStocksDataGridCreate(Action.ActionPerformedEvent event) {
        if (stocksDataGrid.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("StocksBrowseEditMessage"))
                    .show();
            return;
        }
        Stock newStocks = metadata.create(Stock.class);
        Stock merged = getScreenData().getDataContext().merge(newStocks);
        merged.setOwner(owner);
        stocksDc.getMutableItems().add(merged);
        execAction = "create";
        stocksDataGrid.edit(merged);
    }

    @Subscribe("stocksDataGrid.edit")
    public void onStocksDataGridEdit(Action.ActionPerformedEvent event) {
        Stock selected = stocksDataGrid.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            stocksDataGrid.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("StocksBrowseSelectItem"))
                    .show();
        }
    }

    @Subscribe("stocksDataGrid")
    public void onStocksDataGridEditorPostCommit(DataGrid.EditorPostCommitEvent event) {
        Stock stocks=(Stock)event.getItem();
        getScreenData().getDataContext().commit();
        dataManager.commit(stocks);
    }

    @Install(to = "stocksDl", target = Target.DATA_LOADER)
    private List<Stock> stocksDlLoadDelegate(LoadContext<Stock> loadContext) {
        List<Stock> list;
        if (owner != null) {
            loadContext.setQueryString("select e from orderstore_Stock e where e.owner = :owner");
            LoadContext.Query query = loadContext.getQuery();
            query.setParameter("owner", owner);
        }
        list = dataManager.loadList(loadContext);
        return list;
    }
    @Subscribe("stocksDataGrid")
    public void onStocksDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        if ( execAction.equals("create") )
            stocksDc.getMutableItems().remove((Stock) event.getItem());
    }
}