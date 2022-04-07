package com.mall4j.cloud.product.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SpuCategory implements Serializable {
    private Long spuCategoryId;

    private Long categoryId;

    private Long spuId;

    private Date createTime;

    private Date updateTime;

    private String tagsDescribe;

    private Long shopId;

    private static final long serialVersionUID = 1L;

    public Long getSpuCategoryId() {
        return spuCategoryId;
    }

    public void setSpuCategoryId(Long spuCategoryId) {
        this.spuCategoryId = spuCategoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTagsDescribe() {
        return tagsDescribe;
    }

    public void setTagsDescribe(String tagsDescribe) {
        this.tagsDescribe = tagsDescribe == null ? null : tagsDescribe.trim();
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spuCategoryId=").append(spuCategoryId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", spuId=").append(spuId);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", tagsDescribe=").append(tagsDescribe);
        sb.append(", shopId=").append(shopId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}