package com.sweethill.orderstore.web.screens.orderproduct;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.StatusEntity;
import com.sweethill.orderstore.entity.stock.Stock;
import com.sweethill.orderstore.entity.production.management.*;
import com.sweethill.orderstore.service.OrderStoreService;
import com.sweethill.orderstore.web.screens.statusentity.StatusEntityBrowse;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @Inject
    private UniqueNumbersService uniqueNumbersService;

    @Subscribe
    public void onInitEntity(InitEntityEvent<OrderProduct> event) {
        Stock stock = orderStoreService.getDefaultStock();
        OrderProduct orderProduct = event.getEntity();
        orderProduct.setOwner(orderStoreService.getCurrentUserOwner());
        orderProduct.setOrderDate(new Date());
        orderProduct.setOrderNum(Long.toString(uniqueNumbersService.getNextNumber(("OrderProductNumber"))));
        orderProduct.setStockMaterials(stock);
        orderProduct.setStockProduct(stock);
    }

    @Subscribe("itemsTable.create")
    public void onItemsTableCreate(Action.ActionPerformedEvent event) {
        if (itemsTable.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("itemsEditMessage"))
                    .show();
            return;
        }
        OrderProductItem orderProductItem = metadata.create(OrderProductItem.class);
        OrderProductItem merged = getScreenData().getDataContext().merge(orderProductItem);
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

    @Subscribe("itemsTable")
    public void onItemsTableEditorOpen(DataGrid.EditorOpenEvent event) {
        Map fields = event.getFields();
        PickerField fieldProduct = (PickerField) fields.get("product");
        PickerField fieldUnit = (PickerField) fields.get("unit");
        PickerField fieldSpecification = (PickerField) fields.get("specification");

        fieldProduct.addValueChangeListener(e -> {
            Goods good = (Goods) fieldProduct.getValue();
            if (good != null) {
                fieldUnit.setValue(good.getUnit());
                PickerField.LookupAction action = (PickerField.LookupAction) fieldSpecification.getAction("lookup");
                if (action != null)
                    action.setLookupScreenParams(ParamsMap.of("product", good));
            }
        });
    }

    @Subscribe("materialsTable")
    public void onMaterialsTableEditorOpen(DataGrid.EditorOpenEvent event) {
        Map fields = event.getFields();
        PickerField fieldGoog = (PickerField) fields.get("good");
        PickerField fieldUnit = (PickerField) fields.get("unit");

        fieldGoog.addValueChangeListener(e -> {
            Goods good = (Goods) fieldGoog.getValue();
            if (good != null) {
                fieldUnit.setValue(good.getUnit());
            }
        });
    }

    @Subscribe("materialsTable.fill")
    public void onMaterialsTableFill(Action.ActionPerformedEvent event) {
        List<OrderProductItem> items = itemsDc.getMutableItems();
        OrderProduct orderProduct = getEditedEntity();
        if (items.size() > 0) {
            materialsDc.getMutableItems().clear();
            for (OrderProductItem orderProductItem : items) {
                ProductSpecification specification = orderProductItem.getSpecification();
                if (specification != null) {
                    List<RowMaterial> materials = specification.getMaterials();
                    if (materials.size() > 0) {
                        for (RowMaterial itemMaterial : materials) {
                            OrderProductMaterial material = metadata.create(OrderProductMaterial.class);
                            material.setGood(itemMaterial.getGood());
                            material.setQuantity(itemMaterial.getQuantity());
                            material.setUnit(itemMaterial.getUnit());
                            material.setNote(itemMaterial.getNote());
                            material.setOrderProduct(orderProduct);
                            OrderProductMaterial merged = getScreenData().getDataContext().merge(material);
                            materialsDc.getMutableItems().add(merged);
                        }
                    }
                }
            }
        }
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        OrderProduct orderProduct = getEditedEntity();
        if (!PersistenceHelper.isNew(orderProduct)) {
            String caption = messageBundle.getMessage("editInstanceCaption");
            Date orderDate = orderProduct.getOrderDate();
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            caption = caption
                    .replace("%n", orderProduct.getOrderNum())
                    .replace("%d", dateFormat.format(orderDate));
            getWindow().setCaption(caption);
        }
    }

    @Install(to = "statusField.lookup", subject = "screenOptionsSupplier")
    private ScreenOptions statusFieldLookupScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("entityType", "OrderProduct"));
    }
}