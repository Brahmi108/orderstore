package com.sweethill.orderstore.web.screens.goodsgroups;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.GoodsGroups;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_GoodsGroups.browse")
@UiDescriptor("goods-groups-browse.xml")
@LookupComponent("goodsGroupsesTable")
@LoadDataBeforeShow
public class GoodsGroupsBrowse extends StandardLookup<GoodsGroups> {
    @Inject
    private CollectionLoader<GoodsGroups> goodsGroupsesDl;
    @Inject
    private OrderStoreService orderStoreService;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        goodsGroupsesDl.setParameter("owner", owner);
    }
}