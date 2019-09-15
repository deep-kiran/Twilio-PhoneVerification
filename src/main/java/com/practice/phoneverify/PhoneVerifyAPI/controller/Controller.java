package com.practice.phoneverify.PhoneVerifyAPI.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.phoneverify.PhoneVerifyAPI.verifier.OTPVerfier;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@RestController
@RequestMapping("/sender")
public class Controller {
	String phonenumber;
	OTPVerfier ot;
	String otp;
	public HashMap<String,String> securityToken;
	Random rand;
	/*
	 * ID and Token from Twilio Dashboard 
	 */
	private final static String AUTH_SID ="***";
	private final static String AUTH_TOKEN ="***";
	
	public Controller() {
		securityToken =new HashMap<String,String>();
		rand =new Random();
	}
	Logger logger = Logger.getLogger(Controller.class.getName()); 
	
	@PostMapping("/send/{pno}")
	public String getPhoneNumber(@PathVariable(value = "pno") String userphonenumber) throws MalformedURLException, ProtocolException, IOException {
		System.out.print(userphonenumber);
		this.phonenumber = userphonenumber;
		actionSend(userphonenumber);
		return phonenumber;
	}
	public String getOTPfromPhone(String userphoneno) {
		return securityToken.get(userphoneno);
	}
	public void genSecurityToken(String phonenumber, String otp) {
		securityToken.put(phonenumber,otp);
	}
	@PostMapping("/get/{pno}/{otpid}")	
	public int verify(@PathVariable(value = "pno") String userphonenumber, @PathVariable(value = "otpid") String userInputOTP) {
		return ot.verifyOTP(userphonenumber, userInputOTP);
	}

	public void actionSend(String userphonenumber) throws MalformedURLException, ProtocolException, IOException {
		Twilio.init(AUTH_SID, AUTH_TOKEN);
		otp = new Integer(rand.nextInt(9999)).toString();
		Message message = Message
                .creator(new PhoneNumber("+"+userphonenumber), // to
                        new PhoneNumber("+19284517388"), // from
                        "Your otp is "+ otp)
                .create();
        System.out.println(message.getSid());
	}
}
