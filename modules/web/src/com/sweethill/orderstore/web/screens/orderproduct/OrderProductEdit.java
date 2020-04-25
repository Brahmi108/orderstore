package com.sweethill.orderstore.web.screens.orderproduct;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.production.management.OrderProduct;

@UiController("orderstore_OrderProduct.edit")
@UiDescriptor("order-product-edit.xml")
@EditedEntityContainer("orderProductDc")
@LoadDataBeforeShow
public class OrderProductEdit extends StandardEditor<OrderProduct> {
}