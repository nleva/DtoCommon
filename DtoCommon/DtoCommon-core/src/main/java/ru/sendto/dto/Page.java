package ru.sendto.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base class to create paged responses
 * 
 * @author Lev Nadeinsky
 */
@Data
@EqualsAndHashCode(callSuper = false) 
@JsonTypeName("p")
public abstract class Page<T extends Dto> extends RequestInfo {
	List<T> list;
	int current;
	int total;
}