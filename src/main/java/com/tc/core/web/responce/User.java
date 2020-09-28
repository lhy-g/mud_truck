package com.tc.core.web.responce;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@TableName("main_user")
@Data
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String nickname;

    private String phone;

    private String headPortraitLink;

    private LocalDateTime createTime;
}
