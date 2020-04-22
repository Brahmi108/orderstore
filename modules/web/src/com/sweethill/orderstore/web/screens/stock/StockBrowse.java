package com.sweethill.orderstore.web.screens.stock;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.Stock;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_Stock.browse")
@UiDescriptor("stock-browse.xml")
@LookupComponent("stocksTable")
@LoadDataBeforeShow
public class StockBrowse extends StandardLookup<Stock> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private CollectionLoader<Stock> stocksDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        stocksDl.setParameter("owner", owner);
    }
}