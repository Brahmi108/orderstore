package com.sweethill.orderstore.web.screens.orderproduct;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.production.management.OrderProduct;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_OrderProduct.screen")
@UiDescriptor("order-product-screen.xml")
@LookupComponent("orderProductsTable")
@LoadDataBeforeShow
public class OrderProductScreen extends StandardLookup<OrderProduct> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private CollectionLoader<OrderProduct> orderProductsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        orderProductsDl.setParameter("owner", owner);
    }
}