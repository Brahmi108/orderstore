package com.sweethill.orderstore.entity.production.management;

import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.Creatable;
import com.haulmont.cuba.core.entity.Updatable;
import com.haulmont.cuba.core.entity.Versioned;
import com.sweethill.orderstore.entity.Goods;
import com.sweethill.orderstore.entity.Owner;
import com.sweethill.orderstore.entity.Units;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NamePattern("%s %s %s|product,quantity,unit")
@Table(name = "ORDERSTORE_PRODUCT_SPECIFICATION")
@Entity(name = "orderstore_ProductSpecification")
public class ProductSpecification extends BaseUuidEntity implements Creatable, Updatable, Versioned {
    private static final long serialVersionUID = 3319888996703561794L;

    @NotNull
    @Column(name = "SPEC_NUMBER", nullable = false, length = 50)
    protected String specNumber;

    @Temporal(TemporalType.DATE)
    @NotNull
    @Column(name = "SPEC_DATE", nullable = false)
    protected Date specDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PRODUCT_ID")
    protected Goods product;

    @NotNull
    @Column(name = "QUANTITY", nullable = false)
    protected Double quantity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "UNIT_ID")
    protected Units unit;

    @Column(name = "ACTIVE")
    protected Boolean active = false;

    @Composition
    @OneToMany(mappedBy = "productSpecification")
    protected List<RowMaterial> materials;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID")
    protected Owner owner;

    @Lob
    @Column(name = "INSTRUCTION")
    protected String instruction;

    @Column(name = "COMMENT_", length = 1000)
    protected String notes;

    @Column(name = "CREATE_TS")
    protected Date createTs;

    @Column(name = "CREATED_BY", length = 50)
    protected String createdBy;

    @Column(name = "UPDATE_TS")
    protected Date updateTs;

    @Column(name = "UPDATED_BY", length = 50)
    protected String updatedBy;

    @Version
    @Column(name = "VERSION", nullable = false)
    protected Integer version;

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getSpecDate() {
        return specDate;
    }

    public void setSpecDate(Date specDate) {
        this.specDate = specDate;
    }

    public String getSpecNumber() {
        return specNumber;
    }

    public void setSpecNumber(String specNumber) {
        this.specNumber = specNumber;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public List<RowMaterial> getMaterials() {
        return materials;
    }

    public void setMaterials(List<RowMaterial> materials) {
        this.materials = materials;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Goods getProduct() {
        return product;
    }

    public void setProduct(Goods product) {
        this.product = product;
    }
}