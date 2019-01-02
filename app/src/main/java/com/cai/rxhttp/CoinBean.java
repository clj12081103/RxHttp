package com.cai.rxhttp;

import com.google.gson.annotations.SerializedName;



public class CoinBean {

    private String stockCode;
    private String stockName;
    private String stockType;
    //是否可充值
    private String canRecharge;
    //是否可提现
    private String canWithdraw;
    //币种
    @SerializedName(value = "remark")
    private String symbol;
    //充值地址
    private String walletAddr;
    //二维码字符串
    private String qrcodeStr;

    public String getWalletAddr() {
        return walletAddr;
    }

    public void setWalletAddr(String walletAddr) {
        this.walletAddr = walletAddr;
    }

    public String getQrcodeStr() {
        return qrcodeStr;
    }

    public void setQrcodeStr(String qrcodeStr) {
        this.qrcodeStr = qrcodeStr;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    public String isCanRecharge() {
        return canRecharge;
    }

    public void setCanRecharge(String canRecharge) {
        this.canRecharge = canRecharge;
    }

    public String isCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(String canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setRemark(String remark) {
        this.symbol = remark;
    }
}
