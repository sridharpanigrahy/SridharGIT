package com.example.SpringBootApp1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class Greeting
{
    private final long id;
    @NotNull
    private final String message;
}
