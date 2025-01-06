/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

package com.oea.online_exam_app.Enums;

/**
 *
 * @author tirth
 */
public enum ExamStatusCodeEnum {
    InProgress(1),
    Timeout(2),
    AlreadySubmitted(3),
    SomeThingWentWrong(4),
    ServerError(5),
    InvalidCode(6),
    NotStarted(7),
    DeviceChanged(8);
    private final int code;

    ExamStatusCodeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
