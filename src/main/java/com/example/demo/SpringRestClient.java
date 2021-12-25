package com.example.demo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.Email;


public class SpringRestClient {

    private static final String EMPLOYEES_ENDPOINT_URL = "http://91.241.64.178:7081/api/users";
    private static final String DELETE_EMPLOYEE_ENDPOINT_URL = "http://91.241.64.178:7081/api/users/3";
    private static RestTemplate restTemplate = new RestTemplate();
    private static String cookie2;
    private static String b1;
    private static String b2;
    private static String b3;

    public static void main(String[] args) {

        SpringRestClient springRestClient = new SpringRestClient();

        springRestClient.getEmployees();

        springRestClient.createEmployee();

        springRestClient.updateEmployee();

        springRestClient.deleteEmployee();
        String answer = b1+b2+b3;
        System.out.println(answer);
    }

    public void getEmployees() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(EMPLOYEES_ENDPOINT_URL, HttpMethod.GET, entity,
                String.class);
        String cookie = result.getHeaders().get("Set-Cookie").get(0);
        String[] result1 = cookie.split(";");

        System.out.println(result);
        cookie2 = result1[0];

    }


    private void createEmployee() {
        String json = "{\"id\":3,\"name\":\"James\",\"lastName\":\"Brown\",\"age\":21}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
      headers.add("Cookie", cookie2);
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> answer = restTemplate.postForEntity(EMPLOYEES_ENDPOINT_URL, entity, String.class);
        b1 = answer.getBody();


    }

    private void updateEmployee() {
        String json = "{\"id\":3,\"name\":\"Thomas\",\"lastName\":\"Shelby\",\"age\":21}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie2);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, String> param = new HashMap<String, String>();
        param.put("id","3");
        HttpEntity<String> entity = new HttpEntity<>(json, headers);
        ResponseEntity<String> answer = restTemplate.exchange(EMPLOYEES_ENDPOINT_URL, HttpMethod.PUT, entity, String.class, param);
        b2 = answer.getBody();
    }

    private void deleteEmployee() {

        Map<String, Long> params = new HashMap<>();
        params.put("id", 3L);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie2);
        HttpEntity<String> entity = new HttpEntity(params, headers);
        ResponseEntity<String> answer = restTemplate.exchange(DELETE_EMPLOYEE_ENDPOINT_URL, HttpMethod.DELETE, entity, String.class);
        b3 = answer.getBody();
    }
}
