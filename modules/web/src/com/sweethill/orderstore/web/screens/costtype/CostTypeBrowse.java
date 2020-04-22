package com.sweethill.orderstore.web.screens.costtype;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.CostType;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_CostType.browse")
@UiDescriptor("cost-type-browse.xml")
@LookupComponent("costTypesTable")
@LoadDataBeforeShow
public class CostTypeBrowse extends StandardLookup<CostType> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private CollectionLoader<CostType> costTypesDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        costTypesDl.setParameter("owner", owner);
    }
}