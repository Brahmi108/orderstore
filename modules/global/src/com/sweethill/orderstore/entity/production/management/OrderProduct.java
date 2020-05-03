package com.sweethill.orderstore.entity.production.management;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.Creatable;
import com.haulmont.cuba.core.entity.Updatable;
import com.haulmont.cuba.core.entity.Versioned;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.StatusEntity;
import com.sweethill.orderstore.entity.Stock;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NamePattern("%s %s|orderNum,orderDate")
@Table(name = "ORDERSTORE_ORDER_PRODUCT")
@Entity(name = "orderstore_OrderProduct")
public class OrderProduct extends BaseUuidEntity implements Versioned, Updatable, Creatable {
    private static final long serialVersionUID = -6767684362269254075L;

    @NotNull
    @Column(name = "NUM", nullable = false, length = 100)
    protected String orderNum;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "ORDER_DATE", nullable = false)
    protected Date orderDate;

    @Column(name = "ORDER_NOTE", length = 2000)
    protected String orderNote;

    @Temporal(TemporalType.DATE)
    @Column(name = "READY_DATE")
    protected Date readyDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID")
    protected Owner owner;

    @Composition
    @OneToMany(mappedBy = "orderProduct")
    protected List<OrderProductItem> items;

    @Composition
    @OneToMany(mappedBy = "orderProduct")
    protected List<OrderProductMaterial> materials;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STOCK_PRODUCT_ID")
    protected Stock stockProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_MATERIALS_ID")
    protected Stock stockMaterials;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STATUS_ID")
    protected StatusEntity status;

    @Column(name = "UPDATE_TS")
    protected Date updateTs;

    @Column(name = "UPDATED_BY", length = 50)
    protected String updatedBy;

    @Column(name = "CREATE_TS")
    protected Date createTs;

    @Column(name = "CREATED_BY", length = 50)
    protected String createdBy;

    @Version
    @Column(name = "VERSION", nullable = false)
    protected Integer version;

    public List<OrderProductMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<OrderProductMaterial> materials) {
        this.materials = materials;
    }

    public List<OrderProductItem> getItems() {
        return items;
    }

    public void setItems(List<OrderProductItem> items) {
        this.items = items;
    }

    public Stock getStockMaterials() {
        return stockMaterials;
    }

    public void setStockMaterials(Stock stockMaterials) {
        this.stockMaterials = stockMaterials;
    }

    public Stock getStockProduct() {
        return stockProduct;
    }

    public void setStockProduct(Stock stockProduct) {
        this.stockProduct = stockProduct;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Date getReadyDate() {
        return readyDate;
    }

    public void setReadyDate(Date readyDate) {
        this.readyDate = readyDate;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreateTs() {
        return createTs;
    }

    @Override
    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdateTs() {
        return updateTs;
    }

    @Override
    public void setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
    }
}