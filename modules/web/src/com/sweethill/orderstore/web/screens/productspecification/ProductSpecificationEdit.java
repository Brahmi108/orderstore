package com.sweethill.orderstore.web.screens.productspecification;

import com.haulmont.cuba.core.app.UniqueNumbersService;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.PersistenceHelper;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.filter.dateinterval.DateInIntervalComponent;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.GoodNameOption;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.Units;
import com.sweethill.orderstore.entity.production.management.ProductSpecification;
import com.sweethill.orderstore.entity.production.management.RowMaterial;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Inject
    private PickerField<Units> unitField;
    @Inject
    private UniqueNumbersService uniqueNumbersService;

    @Subscribe("productField")
    public void onProductFieldValueChange(HasValue.ValueChangeEvent<Goods> event) {
        Goods good = event.getValue();
        if (good != null)
            unitField.setValue(good.getUnit());
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<ProductSpecification> event) {
        ProductSpecification productSpecification = event.getEntity();
        productSpecification.setSpecNumber(Long.toString(uniqueNumbersService.getNextNumber(("SpecificationNumber"))));
        productSpecification.setOwner(orderStoreService.getCurrentUserOwner());
        productSpecification.setSpecDate(new Date());
        productSpecification.setActive(true);
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        ProductSpecification productSpecification = getEditedEntity();
        if (!PersistenceHelper.isNew(productSpecification)) {
            String caption = messageBundle.getMessage("editInstanceCaption");
            Date specdate = productSpecification.getSpecDate();
            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
            caption = caption
                    .replace("%n", productSpecification.getSpecNumber())
                    .replace("%d", dateFormat.format(specdate));
            getWindow().setCaption(caption);
        }
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

    @Subscribe("materialsTable")
    public void onMaterialsTableEditorOpen(DataGrid.EditorOpenEvent editorOpenEvent ) {
        Map fields = editorOpenEvent.getFields();
        PickerField fieldGood = (PickerField) fields.get("good");
        Field fieldUnit = (PickerField) fields.get("unit");

        fieldGood.addValueChangeListener(e -> {
            Goods good = (Goods) fieldGood.getValue();
            if (good != null)
                fieldUnit.setValue(good.getUnit());
        });
    }

}