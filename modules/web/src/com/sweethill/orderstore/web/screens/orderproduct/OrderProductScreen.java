package com.sweethill.orderstore.web.screens.orderproduct;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.production.management.OrderProduct;

@UiController("orderstore_OrderProduct.screen")
@UiDescriptor("order-product-screen.xml")
@LookupComponent("orderProductsTable")
@LoadDataBeforeShow
public class OrderProductScreen extends StandardLookup<OrderProduct> {
}