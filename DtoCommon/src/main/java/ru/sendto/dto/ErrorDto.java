package ru.sendto.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class for exceptions
 * 
 * @author Lev Nadeinsky
 * @date	2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("error")
public class ErrorDto implements Dto {
	String error;
}