package com.tkx.driver.Mappers;

import com.tkx.driver.LoginDetails;
import com.tkx.driver.models.ModelLogin;

public class LoginMapper {
    public static LoginDetails mapLoginDetails(ModelLogin.DataBean login_details){
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setAccess_token(login_details.getAccess_token());
        login_details.setRefresh_token(login_details.getRefresh_token());
        login_details.setTaxi_company(login_details.isTaxi_company());

        return loginDetails;
    }
}
