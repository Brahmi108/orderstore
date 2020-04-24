package com.sweethill.orderstore.web.screens.productspecification;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.production.management.ProductSpecification;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_ProductSpecification.screen")
@UiDescriptor("product-specification-screen.xml")
@LookupComponent("productSpecificationsTable")
@LoadDataBeforeShow
public class ProductSpecificationScreen extends StandardLookup<ProductSpecification> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private CollectionLoader<ProductSpecification> productSpecificationsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        productSpecificationsDl.setParameter("owner", owner);
    }
}