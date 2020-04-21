package com.sweethill.orderstore.web.screens.goods;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.Calendar;
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
    @Inject
    private GroupTable<Goods> goodsesTable;
    @Inject
    private MessageBundle messageBundle;

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

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {
        Calendar cal = Calendar.getInstance();
        goodsesTable.addGeneratedColumn(messageBundle.getMessage("goodsesTable_Cost"),
                new Table.PrintableColumnGenerator<Goods, String>() {
                    @Override
                    public Component generateCell(Goods entity) {
                        Double v_nResult = orderStoreService.getCurrentCost(entity, cal.getTime());
                        if (v_nResult != null)
                            return new Table.PlainTextCell(v_nResult.toString());
                        else
                            return new Table.PlainTextCell("");
                    }
                    @Override
                    public String getValue(Goods entity) {
                        Double v_nResult = orderStoreService.getCurrentCost(entity, cal.getTime());
                        if (v_nResult != null)
                            return v_nResult.toString();
                        else
                            return "";
                    }
                });
    }
    
}