package com.spring.jpa.chap05_practice.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@ToString(exclude = {"post"})
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_hash_tag")
public class HashTag {

    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_no") //객체는 id로 운영할꺼고 컬럼명은 post_no로 들어 갈 것이다.
    private long id;

    private  String tagName; //해시태그 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_no")
    private  Post post;
}
