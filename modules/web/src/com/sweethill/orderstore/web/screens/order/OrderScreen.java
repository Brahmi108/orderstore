package com.sweethill.orderstore.web.screens.order;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.ordering.order.Order;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_Order.screen")
@UiDescriptor("order-screen.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderScreen extends StandardLookup<Order> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private CollectionLoader<Order> ordersDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        ordersDl.setParameter("owner", owner);
    }
}