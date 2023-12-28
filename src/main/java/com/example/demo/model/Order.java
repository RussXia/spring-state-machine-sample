package com.example.demo.model;

import lombok.*;

/**
 * User: RuzzZZ
 * Date: 2023/12/26
 * Time: 16:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Order {

    private String id;

    private Integer status;
}
