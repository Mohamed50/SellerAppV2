package com.example.fifty.sellerappv2;

/**
 * Created by Fifty on 10/14/2018.
 */

public class CompanyItem {
    String companyTypeName;
    int compenyicon;

    public CompanyItem(String companeyTypeName, int departmenticon) {
        this.companyTypeName = companeyTypeName;
        this.compenyicon = departmenticon;
    }

    public String getCompanyTypeName() {
        return companyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }

    public int getCompenyicon() {
        return compenyicon;
    }

    public void setCompenyicon(int compenyicon) {
        this.compenyicon = compenyicon;
    }

}
