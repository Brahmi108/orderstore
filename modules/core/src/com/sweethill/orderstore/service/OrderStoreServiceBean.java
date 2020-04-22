package com.sweethill.orderstore.service;

import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Query;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.sweethill.orderstore.entity.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service(OrderStoreService.NAME)
public class OrderStoreServiceBean implements OrderStoreService {
    @Inject
    private UserSessionSource userSessionSource;
    @Inject
    private DataManager dataManager;
    @Inject
    private Persistence persistence;
    /*
     * Получить текущего владкльца
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
    /*
     * Получить итоговую стоимость товаров в движении по складу
     */
    public Double getStockMovementCost(StockMovement stockMovement)
    {
        Double v_nResult = 0.0;
        if (stockMovement == null ) return null;
        try (final Transaction transaction = persistence.createTransaction()) {
            final EntityManager entityManager = persistence.getEntityManager();
            final Query query = entityManager.createQuery("select sum(x.total) from orderstore_StockRecord x " +
                    "where x.stockMovement = :stockMovement ");
            query.setParameter("stockMovement", stockMovement);
            v_nResult = (Double) query.getFirstResult();
            transaction.commit();
        }
        return v_nResult;
    }
    /*
     * Получить содержимое движенияч по складу
     */
    public List<StockRecord> loadStockRecords(StockMovement stockMovement)
    {
        LoadContext<StockRecord> lc = LoadContext.create(StockRecord.class);
        LoadContext.Query query =
                LoadContext.createQuery("select e from orderstore_StockRecord e where e.stockMovement = :stockMovement")
                        .setParameter("stockMovement", stockMovement);
        lc.setView("stockRecord-view");
        lc.setQuery(query);
        return dataManager.loadList(lc);
    }

    /*
     * Получить склад по умолчанию
     */
    public Stock getDefaultStock()
    {
        Owner owner = getCurrentUserOwner();
        Stock stock = null;
        if (owner == null) return null;
        try (final Transaction transaction = persistence.createTransaction()) {
            final EntityManager entityManager = persistence.getEntityManager();
            final Query query = entityManager.createQuery("select e from orderstore_Stock e where e.def = true and e.owner = :owner");
            query.setParameter("owner", owner);
            stock = (Stock) query.getFirstResult();
            transaction.commit();
        }
        return stock;
    }
}