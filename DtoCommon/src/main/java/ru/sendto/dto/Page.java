package ru.sendto.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Base class to create paged responses
 * 
 * @author Lev Nadeinsky
 * @date	2017-08-07
 */
@Data
@EqualsAndHashCode(callSuper = false) 
@JsonTypeName("p")
public class Page<T extends Dto> extends Dto {
	List<T> list;
	int current;
	int total;
}