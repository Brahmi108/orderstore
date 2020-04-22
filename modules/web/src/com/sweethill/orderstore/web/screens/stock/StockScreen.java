package com.sweethill.orderstore.web.screens.stock;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.Stock;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

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
    @Inject
    private CollectionLoader<Stock> stocksDl;

    @Subscribe
    public void onInit(InitEvent event) {
        owner = orderStoreService.getCurrentUserOwner();
        stocksDl.setParameter("owner", owner);
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

    @Subscribe("stocksDataGrid")
    public void onStocksDataGridEditorClose(DataGrid.EditorCloseEvent event) {
        Stock stock = (Stock) event.getItem();
        if ( execAction.equals("create") ) {
            stocksDc.getMutableItems().remove(stock);
            getScreenData().getDataContext().remove(stock);
        }
    }
}