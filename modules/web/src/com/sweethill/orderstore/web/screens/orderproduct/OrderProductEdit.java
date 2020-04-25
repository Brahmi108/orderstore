package com.sweethill.orderstore.web.screens.orderproduct;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Stock;
import com.sweethill.orderstore.entity.production.management.OrderProduct;
import com.sweethill.orderstore.entity.production.management.OrderProductItem;
import com.sweethill.orderstore.entity.production.management.OrderProductMaterial;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_OrderProduct.edit")
@UiDescriptor("order-product-edit.xml")
@EditedEntityContainer("orderProductDc")
@LoadDataBeforeShow
public class OrderProductEdit extends StandardEditor<OrderProduct> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private DataGrid<OrderProductItem> itemsTable;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    private String execAction;
    @Inject
    private CollectionPropertyContainer<OrderProductItem> itemsDc;
    @Inject
    private Metadata metadata;
    @Inject
    private DataGrid<OrderProductMaterial> materialsTable;
    @Inject
    private CollectionPropertyContainer<OrderProductMaterial> materialsDc;

    @Subscribe
    public void onInitEntity(InitEntityEvent<OrderProduct> event) {
        event.getEntity().setOwner(orderStoreService.getCurrentUserOwner());
    }

    @Subscribe("itemsTable.create")
    public void onItemsTableCreate(Action.ActionPerformedEvent event) {
        if (itemsTable.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("itemsEditMessage"))
                    .show();
            return;
        }
        Stock stock = orderStoreService.getDefaultStock();
        OrderProductItem orderProductItem = metadata.create(OrderProductItem.class);
        OrderProductItem merged = getScreenData().getDataContext().merge(orderProductItem);
        merged.setStockMaterials(stock);
        merged.setStockProduct(stock);
        itemsDc.getMutableItems().add(merged);
        execAction = "create";
        itemsTable.edit(merged);
    }

    @Subscribe("itemsTable.edit")
    public void onItemsTableEdit(Action.ActionPerformedEvent event) {
        OrderProductItem selected = itemsTable.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            itemsTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("itemsSelectItem"))
                    .show();
        }
    }

    @Subscribe("materialsTable.create")
    public void onMaterialsTableCreate(Action.ActionPerformedEvent event) {
        if (materialsTable.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("materialsEditMessage"))
                    .show();
            return;
        }
        OrderProductMaterial orderProductMaterial = metadata.create(OrderProductMaterial.class);
        OrderProductMaterial merged = getScreenData().getDataContext().merge(orderProductMaterial);
        materialsDc.getMutableItems().add(merged);
        execAction = "create";
        materialsTable.edit(merged);

    }

    @Subscribe("materialsTable.edit")
    public void onMaterialsTableEdit(Action.ActionPerformedEvent event) {
        OrderProductMaterial selected = materialsTable.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            materialsTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("materialsSelectItem"))
                    .show();
        }
    }

    @Subscribe("itemsTable")
    public void onItemsTableEditorPreCommit(DataGrid.EditorPreCommitEvent event) {
        OrderProduct orderProduct = getEditedEntity();
        OrderProductItem orderProductItem = (OrderProductItem) event.getItem();
        orderProductItem.setOrderProduct(orderProduct);
    }

    @Subscribe("materialsTable")
    public void onMaterialsTableEditorPreCommit(DataGrid.EditorPreCommitEvent event) {
        OrderProduct orderProduct = getEditedEntity();
        OrderProductMaterial orderProductMaterial = (OrderProductMaterial) event.getItem();
        orderProductMaterial.setOrderProduct(orderProduct);
    }

    @Subscribe("itemsTable")
    public void onItemsTableEditorClose(DataGrid.EditorCloseEvent event) {
        OrderProductItem orderProductItem = (OrderProductItem) event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            itemsDc.getMutableItems().remove(orderProductItem);
            getScreenData().getDataContext().remove(orderProductItem);
        }
        execAction = null;
    }

    @Subscribe("materialsTable")
    public void onMaterialsTableEditorClose(DataGrid.EditorCloseEvent event) {
        OrderProductMaterial orderProductMaterial = (OrderProductMaterial) event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            materialsDc.getMutableItems().remove(orderProductMaterial);
            getScreenData().getDataContext().remove(orderProductMaterial);
        }
        execAction = null;
    }
}