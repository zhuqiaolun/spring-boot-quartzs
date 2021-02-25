package com.demon.springbootquartz.database.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 定时任务
 * </p>
 *
 * @author demon
 * @since 2021-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemJob extends Model<SystemJob> {

    private static final long serialVersionUID=1L;

    @TableId(value = "job_id", type = IdType.AUTO)
    private Integer jobId;

    /**
     * 任务序号
     */
    @TableField("job_index")
    private String jobIndex;

    /**
     * 任务名
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 任务组（类型）
     */
    @TableField("job_group")
    private String jobGroup;

    /**
     * 表达式
     */
    @TableField("job_cron")
    private String jobCron;

    /**
     * 执行状态
     */
    @TableField("job_status")
    private boolean jobStatus;

    /**
     * 描述
     */
    @TableField("job_description")
    private String jobDescription;

    /**
     * 数据
     */
    @TableField("job_data")
    private String jobData;

    /**
     * 初始化状态
     */
    @TableField("job_init_status")
    private boolean jobInitStatus;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;


    @Override
    protected Serializable pkVal() {
        return this.jobId;
    }

}
