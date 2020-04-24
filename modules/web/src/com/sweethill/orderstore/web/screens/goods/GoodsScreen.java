package com.sweethill.orderstore.web.screens.goods;

import com.haulmont.cuba.gui.components.Component;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.Calendar;

@UiController("orderstore_Goods.screen")
@UiDescriptor("goods-screen.xml")
@LookupComponent("goodsesTable")
@LoadDataBeforeShow
public class GoodsScreen extends StandardLookup<Goods> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private GroupTable<Goods> goodsesTable;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private CollectionLoader<Goods> goodsesDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        goodsesDl.setParameter("owner", owner);
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