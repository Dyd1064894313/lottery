package top.duanyd.lottery.entity;

import top.duanyd.lottery.annotation.Column;
import top.duanyd.lottery.annotation.Id;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @Author: duanyandong
 * @Date: 2018/8/19 11:31
 * @Description:
 */
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
    @Column("prize_seven_num")
    private Long prizeSevenNum;
    @Column("prize_seven_every_money")
    private Long prizeSevenEveryMoney;
    @Column("prize_eight_num")
    private Long prizeEightNum;
    @Column("prize_eight_every_money")
    private Long eightEveryMoney;
    @Column("status")
    private Byte status;
    @Column("create_time")
    private Timestamp createTime;
    @Column("update_time")
    private Timestamp updateTime;
    @Column("remark")
    private String remark;
}
