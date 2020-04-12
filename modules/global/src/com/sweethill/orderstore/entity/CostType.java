package com.sweethill.orderstore.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.Creatable;
import com.haulmont.cuba.core.entity.Updatable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "ORDERSTORE_COST_TYPE")
@Entity(name = "orderstore_CostType")
@NamePattern("%s|name")
public class CostType extends BaseUuidEntity implements Creatable, Updatable {
    private static final long serialVersionUID = -820792364683872533L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    protected String name;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OWNER_ID")
    protected Owner owner;

    @Column(name = "DEF")
    protected Boolean def;

    @Column(name = "CREATE_TS")
    protected Date createTs;

    @Column(name = "CREATED_BY", length = 50)
    protected String createdBy;

    @Column(name = "UPDATE_TS")
    protected Date updateTs;

    @Column(name = "UPDATED_BY", length = 50)
    protected String updatedBy;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public Boolean getDef() {
        return def;
    }

    public void setDef(Boolean def) {
        this.def = def;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}