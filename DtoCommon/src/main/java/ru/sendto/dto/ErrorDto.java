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
<<<<<<< Upstream, based on master
public class ErrorDto implements Dto {
=======
public class ErrorDto extends RequestInfo {
>>>>>>> 820eada request type info   request type info added to ErrorDto added to pageResponse
	String error;
}