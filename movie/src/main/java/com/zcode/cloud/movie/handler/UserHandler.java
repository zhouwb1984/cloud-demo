package com.zcode.cloud.movie.handler;

import com.zcode.cloud.movie.constant.PostMode;
import com.zcode.cloud.movie.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.AsyncRestTemplateCustomizer;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouwb
 * @since 2019/1/18
 */
@Component
@Slf4j
public class UserHandler {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private AsyncRestTemplate asyncRestTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Value("${user.server.url}")
    private String userHost;

    public UserModel getUser(Long id) {
        printServiceInstance();
        ResponseEntity<UserModel> responseEntity = restTemplate.getForEntity(
                userHost + "/user/" + id,
                UserModel.class);
        if (responseEntity.getStatusCode()== HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return null;
    }

    public List<UserModel> getUsers(Integer age, String name) {
        printServiceInstance();
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("age", age);
        ResponseEntity<UserModel[]> responseEntity = restTemplate.getForEntity(
                userHost + "/user/get-user-by-condition?name={name}&age={age}",
                UserModel[].class, param);
        if (responseEntity.getStatusCode()== HttpStatus.OK) {
            return Arrays.asList(responseEntity.getBody());
        }
        return null;
    }

    public boolean createUser(UserModel user, PostMode postMode) {

        ResponseEntity<Boolean> responseEntity = null;

        if (postMode.equals(PostMode.BODY_MAP)) {
            Map param = new HashMap();
            param.put("name", user.getName());
            param.put("age", user.getAge());
            responseEntity = restTemplate.postForEntity(
                    userHost + "/user/create-user-map",
                    param, Boolean.class);
        }
        else if (postMode.equals(PostMode.BODY_MODEL)) {
            responseEntity = restTemplate.postForEntity(
                    userHost + "/user/create-user-model",
                    user, Boolean.class);
        }
        else if (postMode.equals(PostMode.PARAMETERS)) {
            responseEntity = restTemplate.postForEntity(
                    userHost + "/user/create-user-param?name={1}&age={2}",
                    null, Boolean.class, user.getName(), user.getAge());
        }
        else if (postMode.equals(PostMode.PARAM_TRANS_MAP)) {
            Map param = new HashMap();
            param.put("name", user.getName());
            param.put("age", user.getAge());
            responseEntity = restTemplate.postForEntity(
                    userHost + "/user/create-user-param?name={name}&age={age}",
                    null, Boolean.class, param);
        }
        else if (postMode.equals(PostMode.EXCHANGE_JSON)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<UserModel> httpEntity = new HttpEntity<>(user, headers);
            log.info("httpEntity:{}", httpEntity);
            responseEntity = restTemplate.exchange(
                    userHost + "/user/create-user-model",
                    HttpMethod.POST, httpEntity, Boolean.class);
        }

        if (responseEntity.getStatusCode()== HttpStatus.OK) {
            return responseEntity.getBody();
        }
        return false;
    }

    public boolean createUserAsync(UserModel user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserModel> httpEntity = new HttpEntity<>(user, headers);
        log.info("httpEntity:{}", httpEntity);
        ListenableFuture<ResponseEntity<Boolean>> future = asyncRestTemplate.exchange(
                userHost + "/user/create-user-model",
                HttpMethod.POST, httpEntity, Boolean.class);
        future.addCallback(new ListenableFutureCallback<ResponseEntity<Boolean>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.error("Call create-user Rest Failed!", throwable);
            }

            @Override
            public void onSuccess(ResponseEntity<Boolean> responseEntity) {
                log.info("Create User Success:{}", responseEntity.getBody());
            }
        });
        return true;
    }

    public boolean createUserWebClient(UserModel user) {
        WebClient webClient = WebClient.create(userHost);
        Mono<Boolean> monoResponse = webClient.post()
                .uri("/user/create-user-model")
                .syncBody(user)
                .retrieve()
                .onStatus(httpStatus -> {
                    log.info("http status code:{}", httpStatus.value());
                    return httpStatus.isError();
                }, clientResponse -> Mono.error(new Exception()))
                .bodyToMono(Boolean.class)
                .doOnError((e) -> {
                    log.error("Create User Failed!", e);
                })
                .doOnSuccess((b) -> {
                    log.info("Greate User Success:{}", b);
                });
        return monoResponse.block();    //Duration.ofMillis(2000L)
    }

    /**
     * 打印user服务的当前节点
     */
    private void printServiceInstance() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("user");
        log.info("current service host : {}:{}:{}",
                serviceInstance.getServiceId(),
                serviceInstance.getHost(),
                serviceInstance.getPort());
    }

}
