package com.barman;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableOAuth2Sso
public class ReportController  extends WebSecurityConfigurerAdapter {


	@Autowired
	private OAuth2ClientContext clientContext;
	
	
	@Autowired
	private OAuth2RestTemplate oauth2restTemplate;
	
	
	@RequestMapping("/")
	public String loadHome(Principal principal) {
		if(principal !=null) {
		System.out.println(	principal.getName());
		return principal.getName();
		}
		else {
			return "Hello, No User found from Principal!"; 
		}
	}
	
	@RequestMapping("/reports")
	public ArrayList<TollUsage> loadReports(Model model) {
		OAuth2AccessToken t =clientContext.getAccessToken();
		System.out.println("Token is: "+t.getValue());
		
		ResponseEntity<ArrayList<TollUsage>> tolls = oauth2restTemplate.exchange("http://localhost:9001/services/tolldata", HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<TollUsage>>(){});
		return tolls.getBody();
	}
	
	@RequestMapping("/test1")
	public ResponseEntity<?> loadHome() throws URISyntaxException {
		
		HttpHeaders HttpHeader = new HttpHeaders();
		HttpHeader.setLocation(new URI("http://www.google.com"));
		
			return new ResponseEntity<String>("Hello from test1", HttpHeader, HttpStatus.OK); 
		}
	

	
	@Override 
	protected void configure(HttpSecurity http) throws Exception { 
		http
	 .authorizeRequests()
	 .antMatchers("/","/login**")
	 .permitAll()
	 .anyRequest()
	 .authenticated(); 
		}
	 
	public static class TollUsage{
		public String id;
		public String stationId;
		public String licensePlate;
		public String timeStamp;
		 
		public TollUsage(){}
		
		public TollUsage(String id,  String stationId, String licensePlate , String timeStamp) {
			
			this.id=id;
			this.stationId=stationId;
			this.licensePlate=licensePlate;
			this.timeStamp=timeStamp;
			
		}
	
	
}
}
