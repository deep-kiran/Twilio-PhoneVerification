package com.practice.phoneverify.PhoneVerifyAPI.verifier;
import org.springframework.web.bind.annotation.RequestMapping;

import com.practice.phoneverify.PhoneVerifyAPI.controller.Controller;

@RequestMapping("/verifier")
public class OTPVerfier {
	public int OTPverif;

	public void setOTP(int OTP) {
		this.OTPverif = OTP;
	}
	Controller c;
	public int getOTP() {
		return OTPverif;
	}
	public void removeFromSession(String userphonenumber) {
		c.securityToken.remove(userphonenumber);
		System.out.print("Phone number verified, deleted from session");
	}
	public int verifyOTP(String userphonenumber,  String userInputOTP) {
		String t =c.getOTPfromPhone(userphonenumber);
		if(t==null) {
			System.out.print("No userphone number found");
			return -1;
		}
		if (t.equals(userInputOTP)) {
			System.out.print("OTP is verified successfully");
			removeFromSession(userphonenumber);
			return 1;
		} else {
			System.out.print("Wrong OTP");
		}
		return 0;
	}
}
