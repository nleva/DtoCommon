package ru.sendto.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false) 
public class ErrorDto extends Dto {
	String error;
}