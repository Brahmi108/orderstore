package com.sweethill.orderstore.web.screens.productspecification;

import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.Field;
import com.haulmont.cuba.gui.components.filter.dateinterval.DateInIntervalComponent;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.GoodNameOption;
import com.sweethill.orderstore.entity.production.management.ProductSpecification;
import com.sweethill.orderstore.entity.production.management.RowMaterial;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.util.Map;

@UiController("orderstore_ProductSpecification.edit")
@UiDescriptor("product-specification-edit.xml")
@EditedEntityContainer("productSpecificationDc")
@LoadDataBeforeShow
public class ProductSpecificationEdit extends StandardEditor<ProductSpecification> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private DataGrid<RowMaterial> materialsTable;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Metadata metadata;
    @Inject
    private CollectionPropertyContainer<RowMaterial> materialsDC;
    private String execAction;

    @Subscribe
    public void onInitEntity(InitEntityEvent<ProductSpecification> event) {
        event.getEntity().setOwner(orderStoreService.getCurrentUserOwner());
    }

    @Subscribe("materialsTable.create")
    public void onMaterialsTableCreate(Action.ActionPerformedEvent event) {
        if (materialsTable.isEditorActive()) {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("materialEditMessage"))
                    .show();
            return;
        }
        RowMaterial rowMaterial = metadata.create(RowMaterial.class);
        RowMaterial merged = getScreenData().getDataContext().merge(rowMaterial);
        materialsDC.getMutableItems().add(merged);
        execAction = "create";
        materialsTable.edit(merged);
    }

    @Subscribe("materialsTable.edit")
    public void onMaterialsTableEdit(Action.ActionPerformedEvent event) {
        RowMaterial selected = materialsTable.getSingleSelected();
        if (selected != null) {
            execAction = "edit";
            materialsTable.edit(selected);
        } else {
            notifications.create()
                    .withCaption(messageBundle.formatMessage("materialSelectItem"))
                    .show();
        }
    }

    @Subscribe("materialsTable")
    public void onMaterialsTableEditorPreCommit(DataGrid.EditorPreCommitEvent event) {
        ProductSpecification productSpecification = getEditedEntity();
        RowMaterial rowMaterial = (RowMaterial) event.getItem();
        rowMaterial.setProductSpecification(productSpecification);

    }

    @Subscribe("materialsTable")
    public void onMaterialsTableEditorClose(DataGrid.EditorCloseEvent event) {
        RowMaterial rowMaterial = (RowMaterial) event.getItem();
        execAction = (execAction == null) ? "edit" : execAction;
        if (execAction.equals("create")) {
            materialsDC.getMutableItems().remove(rowMaterial);
            getScreenData().getDataContext().remove(rowMaterial);
        }
        execAction = null;
    }
}