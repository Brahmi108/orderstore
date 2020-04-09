package com.sweethill.orderstore.service;

import com.sweethill.orderstore.entity.Owner;

public interface OrderStoreService {
    String NAME = "orderstore_OrderStoreService";
    Owner getCurrentUserOwner();
}