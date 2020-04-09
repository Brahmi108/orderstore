package com.sweethill.orderstore.web.screens.goods;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.List;

@UiController("orderstore_Goods.browse")
@UiDescriptor("goods-browse.xml")
@LookupComponent("goodsesTable")
@LoadDataBeforeShow
public class GoodsBrowse extends StandardLookup<Goods> {
    @Inject
    private OrderStoreService orderStoreService;
    private Owner owner;
    @Inject
    private DataManager dataManager;

    protected void setOwner(Owner p_owner) {
        owner = p_owner;
    }

    @Subscribe
    public void onInit(InitEvent event) {
        setOwner(orderStoreService.getCurrentUserOwner());
    }

    @Install(to = "goodsesDl", target = Target.DATA_LOADER)
    private List<Goods> goodsesDlLoadDelegate(LoadContext<Goods> loadContext) {
        List<Goods> list;
        Owner owner=orderStoreService.getCurrentUserOwner();
        if (owner != null) {
            loadContext.setQueryString("select e from orderstore_Goods e where e.owner = :owner");
            LoadContext.Query query = loadContext.getQuery();
            query.setParameter("owner", owner);
        }
        list = dataManager.loadList(loadContext);
        return list;
    }
}