package com.shop.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {
    private Integer id_payment;
    private Integer id_notification;
    private String recipient;
    private String payment_status;
    private String notification_status;
}
