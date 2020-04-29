package com.barman;

import java.util.Arrays;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckTokenController {

	
	private OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(getResourceOwnerPasswordResourceDetails());
		
			
			
			
	public ResourceOwnerPasswordResourceDetails getResourceOwnerPasswordResourceDetails(){
		
		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
		resourceDetails.setAccessTokenUri("http://localhost:9000/services/oauth/token");
		resourceDetails.setScope(Arrays.asList("toll_read"));
		resourceDetails.setClientId("waterdrop");
		resourceDetails.setClientSecret("waterdrop");
		resourceDetails.setUsername("user2");
		resourceDetails.setPassword("password2");
		
		
		
		return resourceDetails;
	}
	
			
	
	@RequestMapping("/checkTokenAndGetResource")
	public String checkTokenAndGetResource(){
		String token = oauth2RestTemplate.getAccessToken().toString();
		
		System.out.println(token);
		
		return oauth2RestTemplate.getForObject("http://localhost:9001/services/tolldata", String.class);
	}
	
}
