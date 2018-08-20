package top.duanyd.lottery.entity;

import top.duanyd.lottery.annotation.Column;
import top.duanyd.lottery.annotation.Id;
import top.duanyd.lottery.annotation.Table;

import java.sql.Timestamp;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 10:11
 * @Description:
 */
@Table("lottery_define")
public class DefineEntity {
    @Id
    @Column("id")
    private Long id;
    @Column("lottery_code")
    private String lotteryCodel;
    @Column("lottery_name")
    private String lotteryName;
    @Column("lottery_desc")
    private String lotteryDesc;
    @Column("status")
    private Integer status;
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

    public String getLotteryCodel() {
        return lotteryCodel;
    }

    public void setLotteryCodel(String lotteryCodel) {
        this.lotteryCodel = lotteryCodel;
    }

    public String getLotteryName() {
        return lotteryName;
    }

    public void setLotteryName(String lotteryName) {
        this.lotteryName = lotteryName;
    }

    public String getLotteryDesc() {
        return lotteryDesc;
    }

    public void setLotteryDesc(String lotteryDesc) {
        this.lotteryDesc = lotteryDesc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
