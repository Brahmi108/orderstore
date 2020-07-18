package com.sweethill.orderstore.web.screens.stockmovement;

import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.stock.StockMovement;
import com.sweethill.orderstore.entity.stock.StockRecord;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.Date;

@UiController("orderstore_StockMovement.edit")
@UiDescriptor("stock-movement-edit.xml")
@EditedEntityContainer("stockMovementDc")
@LoadDataBeforeShow
public class StockMovementEdit extends StandardEditor<StockMovement> {
    private String execAction;
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private DataGrid<StockRecord> stockRecords_Table;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<StockRecord> recordsDC;
    @Inject
    private UniqueNumbersService uniqueNumbersService;

    @Subscribe
    public void onInitEntity(InitEntityEvent<StockMovement> event) {
        StockMovement stockMovement = event.getEntity();
        if (stockMovement.getStock() == null)
            stockMovement.setStock(orderStoreService.getDefaultStock());
        if (stockMovement.getDocDate() == null) {
            Date today = new Date();
            stockMovement.setDocDate(today);
        }
    }

    @Subscribe("stockRecords_Table.create")
    public void onStockRecords_TableCreate(Action.ActionPerformedEvent event) {
        if (stockRecords_Table.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("stockRecords_TableEditMessage"))
                    .show();
            return;
        }
        StockRecord stockRecord = metadata.create(StockRecord.class);
        StockRecord merged = getScreenData().getDataContext().merge(stockRecord);
        recordsDC.getMutableItems().add(merged);
        execAction = "create";
        stockRecords_Table.edit(merged);
    }

    @Subscribe("stockRecords_Table.edit")
    public void onStockRecords_TableEdit(Action.ActionPerformedEvent event) {
        StockRecord selected = stockRecords_Table.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            stockRecords_Table.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("stockRecords_SelectMessage"))
                    .show();
        }
    }

    @Subscribe("stockRecords_Table")
    public void onStockRecords_TableEditorPreCommit(DataGrid.EditorPreCommitEvent<? extends StockRecord> event) {
       StockMovement stockMovement = getEditedEntity();
        StockRecord stockRecord = event.getItem();
        Double price =  (Double)event.getField("price").getValue();
        Double quantity = (Double)event.getField("quantity").getValue();
        price = (price == null)? 0 : price;
        quantity = (quantity == null) ? 0 : quantity;
        stockRecord.setTotal(price * quantity);
        stockRecord.setStockMovement(stockMovement);
    }

    @Subscribe("stockRecords_Table")
    public void onStockRecords_TableEditorClose(DataGrid.EditorCloseEvent<? extends StockRecord> event) {
        StockRecord stockRecord = event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            recordsDC.getMutableItems().remove(stockRecord);
            getScreenData().getDataContext().remove(stockRecord);

        }
        execAction = null;
    }

    @Subscribe
    public void onBeforeCommitChanges(BeforeCommitChangesEvent event) {
        LocalDateTime now = LocalDateTime.now();
        StockMovement stockMovement = getEditedEntity();
        stockMovement.setCreateTime(now);
        stockMovement.setPriority(uniqueNumbersService.getNextNumber(("stockMovementPriority")));
    }
}