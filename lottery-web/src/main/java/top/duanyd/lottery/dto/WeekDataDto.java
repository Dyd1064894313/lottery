package top.duanyd.lottery.dto;

/**
 * Created by Administrator on 2018/8/25.
 */
public class WeekDataDto {

    public enum COLOR{
        red,
        blue;
    }
    private String num;
    private String color;
    private long mondayCount;
    private long tuesdayCount;
    private long wednesdayCount;
    private long thurdayCount;
    private long fridayCount;
    private long saturdayCount;
    private long sundayCount;
    private long totleCount;
    private double mondayPercent;
    private double tuesdayPercent;
    private double wednesdayPercent;
    private double thurdayPercent;
    private double fridayPercent;
    private double saturdayPercent;
    private double sundayPercent;
    private double totlePercent;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getMondayCount() {
        return mondayCount;
    }

    public void setMondayCount(long mondayCount) {
        this.mondayCount = mondayCount;
    }

    public long getTuesdayCount() {
        return tuesdayCount;
    }

    public void setTuesdayCount(long tuesdayCount) {
        this.tuesdayCount = tuesdayCount;
    }

    public long getWednesdayCount() {
        return wednesdayCount;
    }

    public void setWednesdayCount(long wednesdayCount) {
        this.wednesdayCount = wednesdayCount;
    }

    public long getThurdayCount() {
        return thurdayCount;
    }

    public void setThurdayCount(long thurdayCount) {
        this.thurdayCount = thurdayCount;
    }

    public long getFridayCount() {
        return fridayCount;
    }

    public void setFridayCount(long fridayCount) {
        this.fridayCount = fridayCount;
    }

    public long getSaturdayCount() {
        return saturdayCount;
    }

    public void setSaturdayCount(long saturdayCount) {
        this.saturdayCount = saturdayCount;
    }

    public long getSundayCount() {
        return sundayCount;
    }

    public void setSundayCount(long sundayCount) {
        this.sundayCount = sundayCount;
    }

    public long getTotleCount() {
        return totleCount;
    }

    public void setTotleCount(long totleCount) {
        this.totleCount = totleCount;
    }

    public double getMondayPercent() {
        return mondayPercent;
    }

    public void setMondayPercent(double mondayPercent) {
        this.mondayPercent = mondayPercent;
    }

    public double getTuesdayPercent() {
        return tuesdayPercent;
    }

    public void setTuesdayPercent(double tuesdayPercent) {
        this.tuesdayPercent = tuesdayPercent;
    }

    public double getWednesdayPercent() {
        return wednesdayPercent;
    }

    public void setWednesdayPercent(double wednesdayPercent) {
        this.wednesdayPercent = wednesdayPercent;
    }

    public double getThurdayPercent() {
        return thurdayPercent;
    }

    public void setThurdayPercent(double thurdayPercent) {
        this.thurdayPercent = thurdayPercent;
    }

    public double getFridayPercent() {
        return fridayPercent;
    }

    public void setFridayPercent(double fridayPercent) {
        this.fridayPercent = fridayPercent;
    }

    public double getSaturdayPercent() {
        return saturdayPercent;
    }

    public void setSaturdayPercent(double saturdayPercent) {
        this.saturdayPercent = saturdayPercent;
    }

    public double getSundayPercent() {
        return sundayPercent;
    }

    public void setSundayPercent(double sundayPercent) {
        this.sundayPercent = sundayPercent;
    }

    public double getTotlePercent() {
        return totlePercent;
    }

    public void setTotlePercent(double totlePercent) {
        this.totlePercent = totlePercent;
    }
}
