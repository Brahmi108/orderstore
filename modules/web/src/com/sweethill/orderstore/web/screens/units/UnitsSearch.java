package com.sweethill.orderstore.web.screens.units;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.Units;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_Units.browse")
@UiDescriptor("units-search.xml")
@LookupComponent("unitsesTable")
@LoadDataBeforeShow
public class UnitsSearch extends StandardLookup<Units> {
    @Inject
    private CollectionLoader<Units> unitsesDl;
    @Inject
    private OrderStoreService orderStoreService;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        unitsesDl.setParameter("owner", owner);
    }
}