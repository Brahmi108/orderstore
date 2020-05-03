package com.sweethill.orderstore.web.screens.statusentity;

import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.StatusEntity;
import com.sweethill.orderstore.entity.StatusEntityTypes;
import com.sweethill.orderstore.service.OrderStoreService;

import javax.inject.Inject;

@UiController("orderstore_StatusEntity.browse")
@UiDescriptor("status-entity-browse.xml")
@LookupComponent("statusEntitiesTable")
@LoadDataBeforeShow
public class StatusEntityBrowse extends StandardLookup<StatusEntity> {
    @Inject
    private OrderStoreService orderStoreService;
    @Inject
    private CollectionLoader<StatusEntity> statusEntitiesDl;

    @Subscribe
    public void onInit(InitEvent event) {
        Owner owner = orderStoreService.getCurrentUserOwner();
        statusEntitiesDl.setParameter("owner", owner);
        ScreenOptions options = event.getOptions();
        if (options instanceof MapScreenOptions) {
            String entityType = (String) ((MapScreenOptions) options).getParams().get("entityType");
            if (entityType != null)
                statusEntitiesDl.setParameter("entityType", entityType);
        }
    }
}