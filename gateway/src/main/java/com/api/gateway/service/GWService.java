package com.api.gateway.service;

import com.api.gateway.dto.ResponseMessage;
import com.api.gateway.dto.ResponseTokenValid;
import com.api.gateway.dto.data.Order;
import com.api.gateway.dto.data.Products;
import com.api.gateway.dto.data.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;

@Service
public class GWService {

    @Value("${auth.url}")
    private String authUrl;

    @Value("${products.url}")
    private String productsUrl;

    @Value("${order.url}")
    private String orderUrl;

    @Value("${user.url}")
    private String userUrl;

    private final WebClient webClient;


    public GWService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.build();
    }


    public Mono<ResponseTokenValid> checkIsTokenValid(Token token){
        return webClient.post()
                .uri(authUrl)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(token), Token.class)
                .retrieve()
                .bodyToMono(ResponseTokenValid.class);
    }

    public Mono<ResponseMessage> getAllProducts(){
        return webClient.get()
                .uri(productsUrl+"/products")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ResponseMessage.class);
    }

    public Mono<ResponseMessage> getProductById(Integer id){
        return webClient.get()
                .uri(productsUrl+"/product/"+id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ResponseMessage.class);
    }

    public Mono<ResponseMessage> createProduct(Products products){
        return webClient.post()
                .uri(productsUrl+"/product/create")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(products), Products.class)
                .retrieve()
                .bodyToMono(ResponseMessage.class);
    }

    public Mono<ResponseMessage> updateProduct(Products products, Integer id){
        return webClient.put()
                .uri(productsUrl+"/product/update/"+id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(products), Products.class)
                .retrieve()
                .bodyToMono(ResponseMessage.class);
    }

    public Mono<ResponseMessage> deleteProductById(Integer id){
        return webClient.delete()
                .uri(productsUrl+"/product/delete/"+id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ResponseMessage.class);
    }

    public Mono<ResponseMessage> placeOrder(Order order){
        return webClient.post()
                .uri(orderUrl+"/place-order")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(order), Order.class)
                .retrieve()
                .bodyToMono(ResponseMessage.class);
    }

    public Mono<ResponseMessage> placeOrderWithPaymentNotif(Order order){
        try{
            return webClient.post()
                    .uri(orderUrl+"/place-order2")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(Mono.just(order), Order.class)
                    .retrieve()
                    .bodyToMono(ResponseMessage.class);
        }catch (Exception e){
            return null;
        }

    }

    public Flux<ResponseMessage> getAllOrderHist(){
        return webClient.get()
                .uri(orderUrl+"/order-hists")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToFlux(ResponseMessage.class);
    }

    public Mono<ResponseMessage> getOrderHistById(Integer id){
        return webClient.get()
                .uri(orderUrl+"/order-hist/"+id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ResponseMessage.class);
    }
}
