package com.sweethill.orderstore.web.screens.stockmovement;

import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.StockMovement;
import com.sweethill.orderstore.entity.StockRecord;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.List;

@UiController("orderstore_StockMovement.browse")
@UiDescriptor("stock-movement-browse.xml")
@LookupComponent("stockMovementsTable")
@LoadDataBeforeShow
public class StockMovementBrowse extends StandardLookup<StockMovement> {
    @Inject
    private DataGrid<StockMovement> stockMovementsTable;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private CollectionLoader<StockMovement> stockMovementsDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        stockMovementsDl.setParameter("owner", owner);
        stockMovementsTable.setItemClickAction(new BaseAction("itemClickAction")
                .withHandler(actionPerformedEvent ->
                        stockMovementsTable.setDetailsVisible(stockMovementsTable.getSingleSelected(), true)));

        DataGrid.Column column = stockMovementsTable.addGeneratedColumn("totalCost",
            new DataGrid.ColumnGenerator<StockMovement, String>() {
            @Override
            public String getValue(DataGrid.ColumnGeneratorEvent<StockMovement> event) {
                StockMovement stockMovement=event.getItem();
                Double v_nResult = orderStoreService.getStockMovementCost(stockMovement);
                return (v_nResult == null) ? "" : v_nResult.toString();
            }

           @Override
            public Class<String> getType() {
                return String.class;
            }
        }, 3);
        column.setCaption(messageBundle.formatMessage("stockMovementsTable_TotalCaption"));
        column.setWidth(200);
    }

    @Install(to = "stockMovementsTable", subject = "detailsGenerator")
    private Component stockMovementsTableDetailsGenerator(StockMovement entity) {
        VBoxLayout mainLayout = uiComponents.create(VBoxLayout.class);
        mainLayout.setWidth("100%");
        mainLayout.setMargin(true);

        HBoxLayout headerBox = uiComponents.create(HBoxLayout.class);
        headerBox.setWidth("100%");

        Label<String> infoLabel = uiComponents.create(Label.TYPE_STRING);
        infoLabel.setHtmlEnabled(true);
        infoLabel.setStyleName("h1");
        infoLabel.setValue(messageBundle.formatMessage("TableDetails_Caption"));

        Component closeButton = createCloseButton(entity);
        headerBox.add(infoLabel);
        headerBox.add(closeButton);
        headerBox.expand(infoLabel);

        Component content = getContent(entity);

        mainLayout.add(headerBox);
        mainLayout.add(content);
        mainLayout.expand(content);

        return mainLayout;
    }

    private Component getContent(StockMovement entity) {
        Label<String> content = uiComponents.create(Label.TYPE_STRING);
        content.setHtmlEnabled(true);

        StringBuilder sb = new StringBuilder();
        sb.append("<table>")
                .append("<tr>")
                    .append("<th>").append(messageBundle.formatMessage("TableDetails_Good")).append("</th>")
                    .append("<th>&nbsp;</th>")
                    .append("<th>").append(messageBundle.formatMessage("TableDetails_Quantity")).append("</th>")
                    .append("<th>&nbsp;</th>")
                    .append("<th>").append(messageBundle.formatMessage("TableDetails_Price")).append("</th>")
                    .append("<th>&nbsp;</th>")
                    .append("<th>").append(messageBundle.formatMessage("TableDetails_Total")).append("</th>")
                .append("</tr>");

        List<StockRecord> stockRecords = orderStoreService.loadStockRecords(entity);
        double total = 0.0;
        for (StockRecord item : stockRecords) {
            Double cost = item.getPrice();
            Double quantity = item.getQuantity();
            sb.append("<tr>");
                sb.append("<td>").append(item.getGood().getName()).append("</td>");
                sb.append("<td>&nbsp;</td>");
                sb.append("<td>").append(quantity).append("</td>");
                sb.append("<td>&nbsp;</td>");
                sb.append("<td>").append(cost).append("</td>");
                sb.append("<td>&nbsp;</td>");
                sb.append("<td>").append(item.getTotal()).append("</td>");
            sb.append("</tr>");
            total = total + quantity * cost;
        }
        sb.append("<tr>")
                .append("<th>Total:</th>")
                .append("<th></th>").append("<th></th>").append("<th></th>").append("<th></th>").append("<th></th>")
                .append("<th>").append(total).append("</th>")
                .append("</tr>")
                .append("</table>");
        content.setValue(sb.toString());
        return content;
    }

    private Component createCloseButton(StockMovement entity) {
        Button closeButton = uiComponents.create(Button.class);
        closeButton.setIcon("icons/close.png");
        BaseAction closeAction = new BaseAction("closeAction")
                .withHandler(actionPerformedEvent ->
                        stockMovementsTable.setDetailsVisible(entity, false))
                .withCaption("");
        closeButton.setAction(closeAction);
        return closeButton;
    }
}