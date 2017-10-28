package mad.com.myflight.model;



/**
 * contains query information.
 */
public class UserQuery {

    private String mCountry;
    private String mCurrency;
    private String mLocale;
    private String mOriginPlace;
    private String mDestinationPlace;
    private String mOutboundDate;
    private String mInboundDate;
    private boolean mIsDirect;
    private boolean mIsSingleTrip;
    private boolean mIsRoundTrip;
    private int mNumOfAdults;
    private int mNumOfChildren;
    private int mNumOfInfants;
    private String mCabinClass;

    public UserQuery() {}

    public UserQuery(String mCountry, String mCurrency, String mLocale, String mOriginPlace, String mDestinationPlace, String mOutboundDate, String mInboundDate, boolean mIsDirect, boolean mIsSingleTrip, boolean mIsRoundTrip, int mNumOfAdults, int mNumOfChildren, int mNumOfInfants, String mCabinClass) {
        this.mCountry = mCountry;
        this.mCurrency = mCurrency;
        this.mLocale = mLocale;
        this.mOriginPlace = mOriginPlace;
        this.mDestinationPlace = mDestinationPlace;
        this.mOutboundDate = mOutboundDate;
        this.mInboundDate = mInboundDate;
        this.mIsDirect = mIsDirect;
        this.mIsSingleTrip = mIsSingleTrip;
        this.mIsRoundTrip = mIsRoundTrip;
        this.mNumOfAdults = mNumOfAdults;
        this.mNumOfChildren = mNumOfChildren;
        this.mNumOfInfants = mNumOfInfants;
        this.mCabinClass = mCabinClass;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public String getLocale() {
        return mLocale;
    }

    public String getOriginPlace() {
        return mOriginPlace;
    }

    public String getDestinationPlace() {
        return mDestinationPlace;
    }

    public String getOutboundDate() {
        return mOutboundDate;
    }

    public String getInboundDate() {
        return mInboundDate;
    }

    public boolean isDirect() {
        return mIsDirect;
    }

    public boolean isSingleTrip() {return mIsSingleTrip; }

    public boolean isRoundTrip() { return mIsRoundTrip; }

    public int getNumOfAdults() {
        return mNumOfAdults;
    }

    public int getNumOfChildren() {
        return mNumOfChildren;
    }

    public int getNumOfInfants() {
        return mNumOfInfants;
    }

    public String getCabinClass() {
        return mCabinClass;
    }

    public void setCountry(String country) {
        this.mCountry = country;
    }

    public void setCurrency(String currency) {
        this.mCurrency = currency;
    }

    public void setLocale(String locale) {
        this.mLocale = locale;
    }

    public void setOriginPlace(String originPlace) {
        this.mOriginPlace = originPlace;
    }

    public void setDestinationPlace(String destinationPlace) {
        this.mDestinationPlace = destinationPlace;
    }

    public void setOutboundDate(String outboundDate) {
        this.mOutboundDate = outboundDate;
    }

    public void setInboundDate(String inboundDate) {
        this.mInboundDate = inboundDate;
    }

    public void setDirect(boolean isDirect) {
        this.mIsDirect = isDirect;
    }

    public void setSingleTrip (boolean isSingleTrip) { this.mIsSingleTrip = isSingleTrip; }

    public void setRoundTrip (boolean isRoundTrip) { this.mIsRoundTrip = isRoundTrip; }

    public void setNumOfAdults(int numOfAdults) {
        this.mNumOfAdults = numOfAdults;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.mNumOfChildren = numOfChildren;
    }

    public void setNumOfInfants(int numOfInfants) {
        this.mNumOfInfants = numOfInfants;
    }

    public void setCabinClass(String cabinClass) {
        this.mCabinClass = cabinClass;
    }

    @Override
    public String toString() {
        return "UserQuery{" +
                "mCountry='" + mCountry + '\'' +
                ", mCurrency='" + mCurrency + '\'' +
                ", mLocale='" + mLocale + '\'' +
                ", mOriginPlace='" + mOriginPlace + '\'' +
                ", mDestinationPlace='" + mDestinationPlace + '\'' +
                ", mOutboundDate='" + mOutboundDate + '\'' +
                ", mInboundDate='" + mInboundDate + '\'' +
                ", mIsDirect=" + mIsDirect +
                ", mIsSingleTrip" + mIsSingleTrip +
                ", mNumOfAdults=" + mNumOfAdults +
                ", mNumOfChildren=" + mNumOfChildren +
                ", mNumOfInfants=" + mNumOfInfants +
                ", mCabinClass='" + mCabinClass + '\'' +
                '}';
    }
}
