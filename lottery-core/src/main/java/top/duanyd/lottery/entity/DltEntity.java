package top.duanyd.lottery.entity;

import top.duanyd.lottery.annotation.Column;
import top.duanyd.lottery.annotation.Id;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 10:22
 * @Description:
 */
public class DltEntity {
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
    @Column("blue_one")
    private String blueOne;
    @Column("blue_two")
    private String blueTwo;
    @Column("lottery_sale_amount")
    private String lotterySaleAmount;
    @Column("lottery_pool_amount")
    private String lotteryPoolAmount;
    @Column("prize_one_num")
    private Long prizeOneNum;
    @Column("prize_one_every_money")
    private Long prizeOneEveryMoney;
    @Column("prize_one_additional_num")
    private Long prizeOneAdditionalNum;
    @Column("prize_one_additional_every_money")
    private Long prizeOneAdditionalEveryMoney;
    @Column("prize_two_num")
    private Long prizeTwoNum;
    @Column("prize_two_every_money")
    private Long prizeTwoEveryMoney;
    @Column("prize_two_additional_num")
    private Long prizeTwoAdditionalNum;
    @Column("prize_two_additional_every_money")
    private Long prizeTwoAdditionalEveryMoney;
    @Column("prize_three_num")
    private Long prizeThreeNum;
    @Column("prize_three_every_money")
    private Long prizeThreeEveryMoney;
    @Column("prize_three_additional_num")
    private Long prizeThreeAdditionalNum;
    @Column("prize_three_additional_every_money")
    private Long prizeThreeAdditionalEveryMoney;
    @Column("prize_four_num")
    private Long prizeFourNum;
    @Column("prize_four_every_money")
    private Long prizeFourEveryMoney;
    @Column("prize_four_additional_num")
    private Long prizeFourAdditionalNum;
    @Column("prize_four_additional_every_money")
    private Long prizeFourAdditionalEveryMoney;
    @Column("prize_five_num")
    private Long prizeFiveNum;
    @Column("prize_five_every_money")
    private Long prizeFiveEveryMoney;
    @Column("prize_five_additional_num")
    private Long prizeFiveAdditionalNum;
    @Column("prize_five_additional_every_money")
    private Long prizeFiveAdditionalEveryMoney;
    @Column("prize_six_num")
    private Long prizeSixNum;
    @Column("prize_six_every_money")
    private Long prizeSixEveryMoney;
    @Column("prize_six_additional_num")
    private Long prizeSixAdditionalNum;
    @Column("prize_six_additional_every_money")
    private Long prizeSixAdditionalEveryMoney;
    @Column("prize_seven_num")
    private Long prizeSevenNum;
    @Column("prize_seven_every_money")
    private Long prizeSevenEveryMoney;
    @Column("prize_seven_additional_num")
    private Long prizeSevenAdditinalNum;
    @Column("prize_seven_additional_every_money")
    private Long prizeSevenAdditionalEveryMoney;
    @Column("prize_eight_num")
    private Long prizeEightNum;
    @Column("prize_eight_every_money")
    private Long eightEveryMoney;
    @Column("prize_eight_additional_num")
    private Long prizeEightAdditionalNum;
    @Column("prize_eight_additional_every_money")
    private Long prizeEightAdditionalEveryMoney;
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

    public String getBlueOne() {
        return blueOne;
    }

    public void setBlueOne(String blueOne) {
        this.blueOne = blueOne;
    }

    public String getBlueTwo() {
        return blueTwo;
    }

    public void setBlueTwo(String blueTwo) {
        this.blueTwo = blueTwo;
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

    public Long getPrizeOneAdditionalNum() {
        return prizeOneAdditionalNum;
    }

    public void setPrizeOneAdditionalNum(Long prizeOneAdditionalNum) {
        this.prizeOneAdditionalNum = prizeOneAdditionalNum;
    }

    public Long getPrizeOneAdditionalEveryMoney() {
        return prizeOneAdditionalEveryMoney;
    }

    public void setPrizeOneAdditionalEveryMoney(Long prizeOneAdditionalEveryMoney) {
        this.prizeOneAdditionalEveryMoney = prizeOneAdditionalEveryMoney;
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

    public Long getPrizeTwoAdditionalNum() {
        return prizeTwoAdditionalNum;
    }

    public void setPrizeTwoAdditionalNum(Long prizeTwoAdditionalNum) {
        this.prizeTwoAdditionalNum = prizeTwoAdditionalNum;
    }

    public Long getPrizeTwoAdditionalEveryMoney() {
        return prizeTwoAdditionalEveryMoney;
    }

    public void setPrizeTwoAdditionalEveryMoney(Long prizeTwoAdditionalEveryMoney) {
        this.prizeTwoAdditionalEveryMoney = prizeTwoAdditionalEveryMoney;
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

    public Long getPrizeThreeAdditionalNum() {
        return prizeThreeAdditionalNum;
    }

    public void setPrizeThreeAdditionalNum(Long prizeThreeAdditionalNum) {
        this.prizeThreeAdditionalNum = prizeThreeAdditionalNum;
    }

    public Long getPrizeThreeAdditionalEveryMoney() {
        return prizeThreeAdditionalEveryMoney;
    }

    public void setPrizeThreeAdditionalEveryMoney(Long prizeThreeAdditionalEveryMoney) {
        this.prizeThreeAdditionalEveryMoney = prizeThreeAdditionalEveryMoney;
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

    public Long getPrizeFourAdditionalNum() {
        return prizeFourAdditionalNum;
    }

    public void setPrizeFourAdditionalNum(Long prizeFourAdditionalNum) {
        this.prizeFourAdditionalNum = prizeFourAdditionalNum;
    }

    public Long getPrizeFourAdditionalEveryMoney() {
        return prizeFourAdditionalEveryMoney;
    }

    public void setPrizeFourAdditionalEveryMoney(Long prizeFourAdditionalEveryMoney) {
        this.prizeFourAdditionalEveryMoney = prizeFourAdditionalEveryMoney;
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

    public Long getPrizeFiveAdditionalNum() {
        return prizeFiveAdditionalNum;
    }

    public void setPrizeFiveAdditionalNum(Long prizeFiveAdditionalNum) {
        this.prizeFiveAdditionalNum = prizeFiveAdditionalNum;
    }

    public Long getPrizeFiveAdditionalEveryMoney() {
        return prizeFiveAdditionalEveryMoney;
    }

    public void setPrizeFiveAdditionalEveryMoney(Long prizeFiveAdditionalEveryMoney) {
        this.prizeFiveAdditionalEveryMoney = prizeFiveAdditionalEveryMoney;
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

    public Long getPrizeSixAdditionalNum() {
        return prizeSixAdditionalNum;
    }

    public void setPrizeSixAdditionalNum(Long prizeSixAdditionalNum) {
        this.prizeSixAdditionalNum = prizeSixAdditionalNum;
    }

    public Long getPrizeSixAdditionalEveryMoney() {
        return prizeSixAdditionalEveryMoney;
    }

    public void setPrizeSixAdditionalEveryMoney(Long prizeSixAdditionalEveryMoney) {
        this.prizeSixAdditionalEveryMoney = prizeSixAdditionalEveryMoney;
    }

    public Long getPrizeSevenNum() {
        return prizeSevenNum;
    }

    public void setPrizeSevenNum(Long prizeSevenNum) {
        this.prizeSevenNum = prizeSevenNum;
    }

    public Long getPrizeSevenEveryMoney() {
        return prizeSevenEveryMoney;
    }

    public void setPrizeSevenEveryMoney(Long prizeSevenEveryMoney) {
        this.prizeSevenEveryMoney = prizeSevenEveryMoney;
    }

    public Long getPrizeSevenAdditinalNum() {
        return prizeSevenAdditinalNum;
    }

    public void setPrizeSevenAdditinalNum(Long prizeSevenAdditinalNum) {
        this.prizeSevenAdditinalNum = prizeSevenAdditinalNum;
    }

    public Long getPrizeSevenAdditionalEveryMoney() {
        return prizeSevenAdditionalEveryMoney;
    }

    public void setPrizeSevenAdditionalEveryMoney(Long prizeSevenAdditionalEveryMoney) {
        this.prizeSevenAdditionalEveryMoney = prizeSevenAdditionalEveryMoney;
    }

    public Long getPrizeEightNum() {
        return prizeEightNum;
    }

    public void setPrizeEightNum(Long prizeEightNum) {
        this.prizeEightNum = prizeEightNum;
    }

    public Long getEightEveryMoney() {
        return eightEveryMoney;
    }

    public void setEightEveryMoney(Long eightEveryMoney) {
        this.eightEveryMoney = eightEveryMoney;
    }

    public Long getPrizeEightAdditionalNum() {
        return prizeEightAdditionalNum;
    }

    public void setPrizeEightAdditionalNum(Long prizeEightAdditionalNum) {
        this.prizeEightAdditionalNum = prizeEightAdditionalNum;
    }

    public Long getPrizeEightAdditionalEveryMoney() {
        return prizeEightAdditionalEveryMoney;
    }

    public void setPrizeEightAdditionalEveryMoney(Long prizeEightAdditionalEveryMoney) {
        this.prizeEightAdditionalEveryMoney = prizeEightAdditionalEveryMoney;
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
