package com.sweethill.orderstore.web.screens.order;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.PickerField;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.ordering.order.Order;
import com.sweethill.orderstore.entity.ordering.order.OrderItem;
import com.sweethill.orderstore.entity.production.management.OrderProductItem;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@UiController("orderstore_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {
    @Inject
    private UniqueNumbersService uniqueNumbersService;
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private DataGrid<OrderItem> itemsTable;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<OrderItem> itemsDc;
    private String execAction;
    @Subscribe
    public void onInitEntity(InitEntityEvent<Order> event) {
        Order order = event.getEntity();
        order.setOwner(orderStoreService.getCurrentUserOwner());
        order.setOrderDate(new Date());
        order.setOrderNum(Long.toString(uniqueNumbersService.getNextNumber(("OrderNumber"))));
    }
    @Subscribe("itemsTable.create")
    public void onItemsTableCreate(Action.ActionPerformedEvent event) {
        if (itemsTable.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("itemsEditMessage"))
                    .show();
            return;
        }
        OrderItem orderProductItem = metadata.create(OrderItem.class);
        OrderItem merged = getScreenData().getDataContext().merge(orderProductItem);
        itemsDc.getMutableItems().add(merged);
        execAction = "create";
        itemsTable.edit(merged);
    }

    @Subscribe("itemsTable.edit")
    public void onItemsTableEdit(Action.ActionPerformedEvent event) {
        OrderItem selected = itemsTable.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            itemsTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("itemsSelectItem"))
                    .show();
        }
    }

    @Subscribe("itemsTable")
    public void onItemsTableEditorPreCommit(DataGrid.EditorPreCommitEvent event) {
        Order order = getEditedEntity();
        OrderItem orderItem = (OrderItem) event.getItem();
        orderItem.setOrder(order);
    }

    @Subscribe("itemsTable")
    public void onItemsTableEditorClose(DataGrid.EditorCloseEvent event) {
        OrderItem orderItem = (OrderItem) event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            itemsDc.getMutableItems().remove(orderItem);
            getScreenData().getDataContext().remove(orderItem);
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
            if (good != null)
                fieldUnit.setValue(good.getUnit());
        });
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        Order order = getEditedEntity();
        if (!PersistenceHelper.isNew(order)) {
            String caption = messageBundle.getMessage("editInstanceCaption");
            Date orderDate = order.getOrderDate();
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            caption = caption
                    .replace("%n", order.getOrderNum())
                    .replace("%d", dateFormat.format(orderDate));
            getWindow().setCaption(caption);
        }
    }

    @Install(to = "statusField.lookup", subject = "screenOptionsSupplier")
    private ScreenOptions statusFieldLookupScreenOptionsSupplier() {
        return new MapScreenOptions(ParamsMap.of("entityType", "Order"));
    }
}