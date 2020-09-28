package com.tc.core.constant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

@ApiModel("分页查询")
public class PageQuery {

    @ApiModelProperty("行数")
    private Long size;

    @ApiModelProperty("页号")
    private Long current;

    public Long getSize() {
        if (Objects.isNull(size)) {
            return Long.valueOf(10);
        }
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Page getObj() {
        Page page;
        if (Objects.isNull(current)) {
            page = new Page();
        } else {
            page = new Page(this.current, this.size);
        }
        return page;
    }
}
