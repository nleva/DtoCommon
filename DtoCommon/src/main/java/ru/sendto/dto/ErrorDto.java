package ru.sendto.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class for exceptions
 * 
 * @author Lev Nadeinsky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("error")
public class ErrorDto extends Dto {
	String error;
}