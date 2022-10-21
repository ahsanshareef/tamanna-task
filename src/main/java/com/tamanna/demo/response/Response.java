package com.tamanna.demo.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long status;
    private String message;
    private T payload;
}
