package com.spirent.model;

public class PhoneNumber {

    public static final String DEFAULT_COUNTRY_CODE = "7";
    public static final String DEFAULT_CITY_CODE = "812";

    private String countryCode;
    private String cityCode;
    private String number;

    public PhoneNumber(String number){
        this.number = number;
        this.cityCode = DEFAULT_CITY_CODE;
        this.countryCode = DEFAULT_COUNTRY_CODE;
    }

    public PhoneNumber(String cityCode, String number){
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = DEFAULT_COUNTRY_CODE;
    }

    public PhoneNumber(String countryCode, String cityCode, String number){
        this.number = number;
        this.cityCode = cityCode;
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    private String getFormattedNumber() {
        return number.substring(0, 3) + "-" + number.substring(3);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFormattedFullNumber(){
        StringBuilder fullNumber = new StringBuilder();
        fullNumber.append("+");
        fullNumber.append(getCountryCode());
        fullNumber.append(" ");
        fullNumber.append("(");
        fullNumber.append(getCityCode());
        fullNumber.append(")");
        fullNumber.append(" ");
        fullNumber.append(getFormattedNumber());
        return fullNumber.toString();
    }
}
