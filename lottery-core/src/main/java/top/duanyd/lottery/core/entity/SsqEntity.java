package top.duanyd.lottery.core.entity;

import top.duanyd.lottery.core.annotation.Column;
import top.duanyd.lottery.core.annotation.Id;
import top.duanyd.lottery.core.annotation.Table;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 11:31
 * @Description:
 */
@Table("lottery_ssq")
public class SsqEntity {

    @Id
    @Column("id")
    private Long id;
    @Column("lottery_no")
    private String lotteryNo;
    @Column("lottery_date")
    private Date lotteryDate;
    @Column("red_one")
    private String redOne;
    @Column("red_two")
    private String redTwo;
    @Column("red_three")
    private String redThree;
    @Column("red_four")
    private String redFour;
    @Column("red_five")
    private String redFive;
    @Column("red_six")
    private String redSix;
    @Column("blue_one")
    private String blueOne;
    @Column("lottery_sale_amount")
    private String lotterySaleAmount;
    @Column("lottery_pool_amount")
    private String lotteryPoolAmount;
    @Column("prize_one_num")
    private Long prizeOneNum;
    @Column("prize_one_every_money")
    private Long prizeOneEveryMoney;
    @Column("prize_two_num")
    private Long prizeTwoNum;
    @Column("prize_two_every_money")
    private Long prizeTwoEveryMoney;
    @Column("prize_three_num")
    private Long prizeThreeNum;
    @Column("prize_three_every_money")
    private Long prizeThreeEveryMoney;
    @Column("prize_four_num")
    private Long prizeFourNum;
    @Column("prize_four_every_money")
    private Long prizeFourEveryMoney;
    @Column("prize_five_num")
    private Long prizeFiveNum;
    @Column("prize_five_every_money")
    private Long prizeFiveEveryMoney;
    @Column("prize_six_num")
    private Long prizeSixNum;
    @Column("prize_six_every_money")
    private Long prizeSixEveryMoney;
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

    public String getLotteryNo() {
        return lotteryNo;
    }

    public void setLotteryNo(String lotteryNo) {
        this.lotteryNo = lotteryNo;
    }

    public Date getLotteryDate() {
        return lotteryDate;
    }

    public void setLotteryDate(Date lotteryDate) {
        this.lotteryDate = lotteryDate;
    }

    public String getRedOne() {
        return redOne;
    }

    public void setRedOne(String redOne) {
        this.redOne = redOne;
    }

    public String getRedTwo() {
        return redTwo;
    }

    public void setRedTwo(String redTwo) {
        this.redTwo = redTwo;
    }

    public String getRedThree() {
        return redThree;
    }

    public void setRedThree(String redThree) {
        this.redThree = redThree;
    }

    public String getRedFour() {
        return redFour;
    }

    public void setRedFour(String redFour) {
        this.redFour = redFour;
    }

    public String getRedFive() {
        return redFive;
    }

    public void setRedFive(String redFive) {
        this.redFive = redFive;
    }

    public String getRedSix() {
        return redSix;
    }

    public void setRedSix(String redSix) {
        this.redSix = redSix;
    }

    public String getBlueOne() {
        return blueOne;
    }

    public void setBlueOne(String blueOne) {
        this.blueOne = blueOne;
    }

    public String getLotterySaleAmount() {
        return lotterySaleAmount;
    }

    public void setLotterySaleAmount(String lotterySaleAmount) {
        this.lotterySaleAmount = lotterySaleAmount;
    }

    public String getLotteryPoolAmount() {
        return lotteryPoolAmount;
    }

    public void setLotteryPoolAmount(String lotteryPoolAmount) {
        this.lotteryPoolAmount = lotteryPoolAmount;
    }

    public Long getPrizeOneNum() {
        return prizeOneNum;
    }

    public void setPrizeOneNum(Long prizeOneNum) {
        this.prizeOneNum = prizeOneNum;
    }

    public Long getPrizeOneEveryMoney() {
        return prizeOneEveryMoney;
    }

    public void setPrizeOneEveryMoney(Long prizeOneEveryMoney) {
        this.prizeOneEveryMoney = prizeOneEveryMoney;
    }

    public Long getPrizeTwoNum() {
        return prizeTwoNum;
    }

    public void setPrizeTwoNum(Long prizeTwoNum) {
        this.prizeTwoNum = prizeTwoNum;
    }

    public Long getPrizeTwoEveryMoney() {
        return prizeTwoEveryMoney;
    }

    public void setPrizeTwoEveryMoney(Long prizeTwoEveryMoney) {
        this.prizeTwoEveryMoney = prizeTwoEveryMoney;
    }

    public Long getPrizeThreeNum() {
        return prizeThreeNum;
    }

    public void setPrizeThreeNum(Long prizeThreeNum) {
        this.prizeThreeNum = prizeThreeNum;
    }

    public Long getPrizeThreeEveryMoney() {
        return prizeThreeEveryMoney;
    }

    public void setPrizeThreeEveryMoney(Long prizeThreeEveryMoney) {
        this.prizeThreeEveryMoney = prizeThreeEveryMoney;
    }

    public Long getPrizeFourNum() {
        return prizeFourNum;
    }

    public void setPrizeFourNum(Long prizeFourNum) {
        this.prizeFourNum = prizeFourNum;
    }

    public Long getPrizeFourEveryMoney() {
        return prizeFourEveryMoney;
    }

    public void setPrizeFourEveryMoney(Long prizeFourEveryMoney) {
        this.prizeFourEveryMoney = prizeFourEveryMoney;
    }

    public Long getPrizeFiveNum() {
        return prizeFiveNum;
    }

    public void setPrizeFiveNum(Long prizeFiveNum) {
        this.prizeFiveNum = prizeFiveNum;
    }

    public Long getPrizeFiveEveryMoney() {
        return prizeFiveEveryMoney;
    }

    public void setPrizeFiveEveryMoney(Long prizeFiveEveryMoney) {
        this.prizeFiveEveryMoney = prizeFiveEveryMoney;
    }

    public Long getPrizeSixNum() {
        return prizeSixNum;
    }

    public void setPrizeSixNum(Long prizeSixNum) {
        this.prizeSixNum = prizeSixNum;
    }

    public Long getPrizeSixEveryMoney() {
        return prizeSixEveryMoney;
    }

    public void setPrizeSixEveryMoney(Long prizeSixEveryMoney) {
        this.prizeSixEveryMoney = prizeSixEveryMoney;
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
