package com.sweethill.orderstore.service;

import com.sweethill.orderstore.entity.CostType;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.Owner;

import java.util.Date;

public interface OrderStoreService {
    String NAME = "orderstore_OrderStoreService";
    Owner getCurrentUserOwner();
    void setDefaultCostType(CostType currentCostType);
    Double getCurrentCost(Goods good, Date date);
}