package com.sweethill.orderstore.web.screens.goodsgroups;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.GoodsGroups;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.List;

@UiController("orderstore_GoodsGroups.browse")
@UiDescriptor("goods-groups-browse.xml")
@LookupComponent("table")
@LoadDataBeforeShow
public class GoodsGroupsBrowse extends MasterDetailScreen<GoodsGroups> {
    @Inject
    private OrderStoreService orderStoreService;
    private Owner owner;
    @Inject
    private DataManager dataManager;

    protected void setOwner(Owner p_owner) {
        owner = p_owner;
    }

    @Subscribe
    public void onInitEntity(MasterDetailScreen.InitEntityEvent<GoodsGroups> event) {
        event.getEntity().setOwner(owner);
    }

    @Subscribe
    public void onInit(InitEvent event) {
        setOwner(orderStoreService.getCurrentUserOwner());
    }

    @Install(to = "goodsGroupsesDl", target = Target.DATA_LOADER)
    private List<GoodsGroups> goodsGroupsesDlLoadDelegate(LoadContext<GoodsGroups> loadContext) {
        List<GoodsGroups> list;
        Owner owner=orderStoreService.getCurrentUserOwner();
        if (owner != null) {
            loadContext.setQueryString("select e from orderstore_GoodsGroups e where e.owner = :owner");
            LoadContext.Query query = loadContext.getQuery();
            query.setParameter("owner", owner);
        }
        list = dataManager.loadList(loadContext);
        return list;
    }
}