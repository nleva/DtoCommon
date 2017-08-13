package ru.sendto.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class to create requests
 * 
 * @author Lev Nadeinsky
 */
@Data
@EqualsAndHashCode(callSuper = false) 
@JsonTypeName("pr")
public abstract class PageRequest<T extends Dto> extends Dto {
	T request;
	int page;
}