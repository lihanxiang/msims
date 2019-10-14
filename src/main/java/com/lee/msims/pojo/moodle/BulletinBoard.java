package com.lee.msims.pojo.moodle;

import lombok.Data;

/**
 * only post by teacher
 */
@Data
public class BulletinBoard {

    private int id;
    private String courseCode;
    private int size;
}
