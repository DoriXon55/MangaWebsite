package org.dorixon.websiteproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RecaptchaService {
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    @Value("${recaptcha.secret.key}")
    private String secretKey;

    @Autowired
    private RestTemplate restTemplate;

    public boolean verify(String recaptchaResponse) {
        if (recaptchaResponse == null || recaptchaResponse.isEmpty()) {
            return false;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secretKey);
        map.add("response", recaptchaResponse);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        try{
            ResponseEntity<Map> response = restTemplate.postForEntity(RECAPTCHA_VERIFY_URL, request, Map.class);
            Map<String, Object> body = response.getBody();
            return body != null && (Boolean) body.get("success");
        } catch (Exception e)
        {
            return false;
        }

    }

}
