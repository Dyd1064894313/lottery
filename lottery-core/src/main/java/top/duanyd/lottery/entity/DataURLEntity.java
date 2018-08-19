package top.duanyd.lottery.entity;

import top.duanyd.lottery.annotation.Column;
import top.duanyd.lottery.annotation.Id;
import top.duanyd.lottery.annotation.Table;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/8/8.
 */
@Table("lottery_data_url")
public class DataURLEntity {

    @Id
    @Column("id")
    Long id;
    @Column("lottery_code")
    String lotteryCode;
    @Column("url")
    String url;
    @Column("param")
    String param;
    @Column("status")
    private Byte status;
    @Column("create_time")
    private Timestamp createTime;
    @Column("update_time")
    private Timestamp updateTime;
    @Column("remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotteryCode() {
        return lotteryCode;
    }

    public void setLotteryCode(String lotteryCode) {
        this.lotteryCode = lotteryCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
