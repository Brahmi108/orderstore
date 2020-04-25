package com.sweethill.orderstore.web.screens.productspecification;

import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.production.management.ProductSpecification;

@UiController("orderstore_ProductSpecification.browse")
@UiDescriptor("product-specification-browse.xml")
@LookupComponent("productSpecificationsTable")
@LoadDataBeforeShow
public class ProductSpecificationBrowse extends StandardLookup<ProductSpecification> {
}