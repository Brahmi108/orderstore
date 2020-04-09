package com.sweethill.orderstore.web.screens.units;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.Units;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.List;

@UiController("orderstore_Units.search")
@UiDescriptor("units-search.xml")
@LookupComponent("unitsesTable")
@LoadDataBeforeShow
public class UnitsSearch extends StandardLookup<Units> {
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

    @Install(to = "unitsesDl", target = Target.DATA_LOADER)
    private List<Units> unitsesDlLoadDelegate(LoadContext<Units> loadContext) {
        List<Units> list;
        Owner owner=orderStoreService.getCurrentUserOwner();
        if (owner != null) {
            loadContext.setQueryString("select e from orderstore_Units e where e.owner = :owner");
            LoadContext.Query query = loadContext.getQuery();
            query.setParameter("owner", owner);
        }
        list = dataManager.loadList(loadContext);
        return list;
    }
}