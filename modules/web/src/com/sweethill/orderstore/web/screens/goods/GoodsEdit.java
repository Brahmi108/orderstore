package com.sweethill.orderstore.web.screens.goods;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.actions.list.CreateAction;
import com.haulmont.cuba.gui.actions.list.EditAction;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Actions;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.*;
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
    @Inject
    private DataGrid<GoodNameOption> name_optionsTable;
    @Inject
    private Notifications notifications;
    @Inject
    private Actions actions;
    @Inject
    private Metadata metadata;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private CollectionPropertyContainer<GoodNameOption> name_optionsDC;
    @Inject
    private DataGrid<Cost> costsTable;
    @Inject
    private CollectionPropertyContainer<Cost> costsDC;
    private String execAction;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Goods> event) {
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

    @Subscribe("name_optionsTable.create")
    public void onName_optionsTableCreate(Action.ActionPerformedEvent event) {
        if (name_optionsTable.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("name_optionsEditMessage"))
                    .show();
            return;
        }
        GoodNameOption newGoodNameOption = metadata.create(GoodNameOption.class);
        GoodNameOption merged = getScreenData().getDataContext().merge(newGoodNameOption);
        name_optionsDC.getMutableItems().add(merged);
        execAction = "create";
        name_optionsTable.edit(merged);
    }

    @Subscribe("name_optionsTable.edit")
    public void onName_optionsTableEdit(Action.ActionPerformedEvent event) {
        GoodNameOption selected = name_optionsTable.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            name_optionsTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("name_optionsSelectItem"))
                    .show();
        }
    }

    @Subscribe("costsTable.create")
    public void onCostsTableCreate(Action.ActionPerformedEvent event) {
        if (costsTable.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("name_optionsEditMessage"))
                    .show();
            return;
        }
        Cost cost = metadata.create(Cost.class);
        Cost merged = getScreenData().getDataContext().merge(cost);
        costsDC.getMutableItems().add(merged);
        execAction = "create";
        costsTable.edit(merged);
    }

    @Subscribe("costsTable.edit")
    public void onCostsTableEdit(Action.ActionPerformedEvent event) {
        Cost selected = costsTable.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            costsTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("name_optionsSelectItem"))
                    .show();
        }
    }

    @Subscribe("name_optionsTable")
    public void onName_optionsTableEditorPreCommit(DataGrid.EditorPreCommitEvent event) {
        Goods good = getEditedEntity();
        GoodNameOption option = (GoodNameOption) event.getItem();
        option.setGoods(good);
    }

    @Subscribe("costsTable")
    public void onCostsTableEditorPreCommit(DataGrid.EditorPreCommitEvent event) {
        Goods good = getEditedEntity();
        Cost cost = (Cost) event.getItem();
        cost.setGood(good);
    }

    @Subscribe("name_optionsTable")
    public void onName_optionsTableEditorClose(DataGrid.EditorCloseEvent event) {
        GoodNameOption goodNameOption = (GoodNameOption) event.getItem();
        if (execAction.equals("create")) {
            name_optionsDC.getMutableItems().remove(goodNameOption);
            getScreenData().getDataContext().remove(goodNameOption);
        }
    }
    @Subscribe("costsTable")
    public void onCostsTableEditorClose(DataGrid.EditorCloseEvent event) {
        Cost cost = (Cost) event.getItem();
        if (execAction.equals("create")) {
            costsDC.getMutableItems().remove(cost);
            getScreenData().getDataContext().remove(cost);
        }
    }
}