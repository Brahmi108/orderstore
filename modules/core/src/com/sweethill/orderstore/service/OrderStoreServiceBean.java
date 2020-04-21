package com.sweethill.orderstore.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.sweethill.orderstore.entity.CostType;
import com.sweethill.orderstore.entity.ExtUser;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.Owner;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service(OrderStoreService.NAME)
public class OrderStoreServiceBean implements OrderStoreService {
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private DataManager dataManager;
    @Inject
    private Persistence persistence;
    /*
     * Gjkexbnm ntreotuj dkfltkmwf
     */
    public Owner getCurrentUserOwner() {
        Owner owner;
        ExtUser user = (ExtUser) userSessionSource.getUserSession().getCurrentOrSubstitutedUser();
        LoadContext loadUserContext = LoadContext.create(ExtUser.class)
                .setId(user.getId()).setView("user.edit");
        user = (ExtUser) dataManager.load(loadUserContext);
        owner = user.getOwner();
        return owner;
    }
    /*
     * Отметить выбранный тип уены по умолчанию
     */
    public void setDefaultCostType(CostType currentCostType)
    {
        if (currentCostType == null) return;
        Owner owner = currentCostType.getOwner();
        if (owner == null) return;
        if ( currentCostType.getDef() ){
            try (final Transaction transaction = persistence.createTransaction()) {
                final EntityManager entityManager = persistence.getEntityManager();
                final Query query = entityManager.createQuery("update orderstore_CostType e set e.def = false where e.owner = :owner and e <> :current");
                query.setParameter("owner", owner);
                query.setParameter("current", currentCostType);
                query.executeUpdate();
                transaction.commit();
            }
        }
    }
    /*
     * Получить текущую стоимость товара
     */
    public Double getCurrentCost(Goods good, Date date)
    {
        Double v_nResult = 0.0;
        if (good == null | date == null) return null;
        try (final Transaction transaction = persistence.createTransaction()) {
            final EntityManager entityManager = persistence.getEntityManager();
            final Query query = entityManager.createQuery("select x.value from orderstore_Cost x, orderstore_CostType e " +
                    "where x.costType = e "+
                    "and x.good = :good "+
                    "and e.def = true "+
                    "and x.dateBegin <= :date and ( x.dateEnd >= :date or x.dateEnd is null )");
            query.setParameter("good", good);
            query.setParameter("date", date);
            v_nResult = (Double) query.getFirstResult();
            transaction.commit();
        }
        return v_nResult;
    }
}