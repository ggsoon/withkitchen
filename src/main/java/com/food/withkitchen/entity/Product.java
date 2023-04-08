package com.food.withkitchen.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150)
    private String name;

    // 상품의 종류 찌개, 과일
    @Column(nullable = false, length = 50)
    private String type;

    // 상세정보
    private String description;

    @Column(length = 255)
    private String imgUrl;

    private int price;

    private int stock;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int views;
}
