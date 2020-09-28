package com.tc.core.constant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@ApiModel("分页查询")
public class PageParam {

    @ApiModelProperty("行数")
    private Long pageSize;

    @ApiModelProperty("页数")
    private Long pageNum;

    public Long getSize() {
        if (Objects.isNull(pageSize)) {
            return Long.valueOf(10);
        }
        return pageSize;
    }

    public void setSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getCurrent() {
        return pageNum;
    }

    public void setCurrent(Long pageNum) {
        this.pageNum = pageNum;
    }

    public Page getObj() {
        Page page;
        if (Objects.isNull(pageNum)) {
            page = new Page();
        } else {
            page = new Page(this.pageNum, this.pageSize);
        }
        return page;
    }
}
