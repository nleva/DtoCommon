package ru.sendto.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;

import lombok.Data;
import ru.sendto.util.dto.Resolver;

/**
 * Base class for all DTOs.
 * With polymorphic behavior. 
 */

@JsonTypeInfo(use=Id.NAME, include=As.PROPERTY, property="@")
@JsonTypeIdResolver(Resolver.class)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Dto {
}
