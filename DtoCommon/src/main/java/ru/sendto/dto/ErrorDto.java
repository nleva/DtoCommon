package ru.sendto.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Class for exceptions
 * 
 * @author Lev Nadeinsky
 */
@Data
@EqualsAndHashCode(callSuper = false)
@JsonTypeName("error")
public class ErrorDto extends RequestInfo {
	String error;
}