package com.sweethill.orderstore.web.screens.goods;

import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.GoodsGroups;
import com.sweethill.orderstore.entity.Units;
import com.sweethill.orderstore.service.OrderStoreService;
import com.sweethill.orderstore.web.screens.goodsgroups.GoodsGroupsSearch;
import com.sweethill.orderstore.web.screens.units.UnitsSearch;

import javax.inject.Inject;

@UiController("orderstore_Goods.edit")
@UiDescriptor("goods-edit.xml")
@EditedEntityContainer("goodsDc")
@LoadDataBeforeShow
public class GoodsEdit extends StandardEditor<Goods> {
    @Inject
    private PickerField<Units> unitField;

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private PickerField<GoodsGroups> groupField;
    @Inject
    private OrderStoreService orderStoreService;

    @Subscribe
    public void onInitEntity1(InitEntityEvent<Goods> event) {
        event.getEntity().setOwner(orderStoreService.getCurrentUserOwner());
    }

    @Subscribe("unitField.lookup")
    public void onUnitFieldLookup(Action.ActionPerformedEvent event) {
        UnitsSearch browse = screenBuilders.lookup(Units.class, this)
                .withField(unitField)
                .withScreenClass(UnitsSearch.class) // specific lookup screen
                .withLaunchMode(OpenMode.DIALOG)    // open as modal dialog
                .build();
        browse.show();
    }

    @Subscribe("groupField.lookup")
    public void onGroupFieldLookup(Action.ActionPerformedEvent event) {
        GoodsGroupsSearch browse = screenBuilders.lookup(GoodsGroups.class, this)
                .withField(groupField)
                .withScreenClass(GoodsGroupsSearch.class) // specific lookup screen
                .withLaunchMode(OpenMode.DIALOG)    // open as modal dialog
                .build();
        browse.show();
    }
    
}