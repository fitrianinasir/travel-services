package com.shop.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PushPaymentDTO {
    private Integer order_id;
    private String recipient;
    private String payment_type;
    private String product_name;
    private Integer amount;
    private Integer price;
    private Integer total_payment;
}