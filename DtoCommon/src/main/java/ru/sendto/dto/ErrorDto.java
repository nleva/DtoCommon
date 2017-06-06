package ru.sendto.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false) 
public class ErrorDto implements Dto {
	String error;
}