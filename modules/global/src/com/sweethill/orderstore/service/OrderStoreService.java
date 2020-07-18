package com.sweethill.orderstore.service;

import com.sweethill.orderstore.entity.*;
import com.sweethill.orderstore.entity.stock.Stock;
import com.sweethill.orderstore.entity.stock.StockMovement;
import com.sweethill.orderstore.entity.stock.StockRecord;

import java.util.Date;
import java.util.List;

public interface OrderStoreService {
    String NAME = "orderstore_OrderStoreService";
    Owner getCurrentUserOwner();
    void setDefaultCostType(CostType currentCostType);
    Double getCurrentCost(Goods good, Date date);
    Double getStockMovementCost(StockMovement stockMovement);
    List<StockRecord> loadStockRecords(StockMovement stockMovement);
    Stock getDefaultStock();
    Double getStockRest(Goods good, Date date, Stock stock);
}