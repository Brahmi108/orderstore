package com.sweethill.orderstore.web.screens.goods;

import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_Goods.seaarch")
@UiDescriptor("goods-search.xml")
@LookupComponent("goodsesTable")
@LoadDataBeforeShow
public class GoodsSearch extends StandardLookup<Goods> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private CollectionLoader<Goods> goodsesDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        goodsesDl.setParameter("owner", owner);
    }
}