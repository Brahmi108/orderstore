package com.sweethill.orderstore.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.sweethill.orderstore.entity.ExtUser;
import com.sweethill.orderstore.entity.Owner;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(OrderStoreService.NAME)
public class OrderStoreServiceBean implements OrderStoreService {
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private DataManager dataManager;

    public Owner getCurrentUserOwner() {
        Owner owner;
        ExtUser user = (ExtUser) userSessionSource.getUserSession().getCurrentOrSubstitutedUser();
        LoadContext loadUserContext = LoadContext.create(ExtUser.class)
                .setId(user.getId()).setView("user.edit");
        user = (ExtUser) dataManager.load(loadUserContext);
        owner = user.getOwner();
        return owner;
    }

}