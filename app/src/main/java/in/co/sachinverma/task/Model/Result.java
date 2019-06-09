package in.co.sachinverma.task.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("CurrencyLong")
    @Expose
    private String currencyLong;
    @SerializedName("MinConfirmation")
    @Expose
    private Integer minConfirmation;
    @SerializedName("TxFee")
    @Expose
    private Double txFee;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsRestricted")
    @Expose
    private Boolean isRestricted;
    @SerializedName("CoinType")
    @Expose
    private String coinType;
    @SerializedName("BaseAddress")
    @Expose
    private String baseAddress;
    @SerializedName("Notice")
    @Expose
    private Object notice;

    /**
     * No args constructor for use in serialization
     */

    public Result() {
    }

    /**
     *
     * @param isActive
     * @param isRestricted
     * @param coinType
     * @param minConfirmation
     * @param currencyLong
     * @param txFee
     * @param notice
     * @param baseAddress
     * @param currency
     */

    public Result(String currency,
                  String currencyLong,
                  Integer minConfirmation,
                  Double txFee,
                  Boolean isActive,
                  Boolean isRestricted,
                  String coinType,
                  String baseAddress,
                  Object notice) {
        super();
        this.currency = currency;
        this.currencyLong = currencyLong;
        this.minConfirmation = minConfirmation;
        this.txFee = txFee;
        this.isActive = isActive;
        this.isRestricted = isRestricted;
        this.coinType = coinType;
        this.baseAddress = baseAddress;
        this.notice = notice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyLong() {
        return currencyLong;
    }

    public void setCurrencyLong(String currencyLong) {
        this.currencyLong = currencyLong;
    }

    public Integer getMinConfirmation() {
        return minConfirmation;
    }

    public void setMinConfirmation(Integer minConfirmation) {
        this.minConfirmation = minConfirmation;
    }

    public Double getTxFee() {
        return txFee;
    }

    public void setTxFee(Double txFee) {
        this.txFee = txFee;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsRestricted() {
        return isRestricted;
    }

    public void setIsRestricted(Boolean isRestricted) {
        this.isRestricted = isRestricted;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }

    public Object getNotice() {
        return notice;
    }

    public void setNotice(Object notice) {
        this.notice = notice;
    }

    @Override
    public String toString() {
        return "Result{" +
                "currency='" + currency + '\'' +
                ", currencyLong='" + currencyLong + '\'' +
                ", minConfirmation=" + minConfirmation +
                ", txFee=" + txFee +
                ", isActive=" + isActive +
                ", isRestricted=" + isRestricted +
                ", coinType='" + coinType + '\'' +
                ", baseAddress='" + baseAddress + '\'' +
                ", notice=" + notice +
                '}';
    }
}
