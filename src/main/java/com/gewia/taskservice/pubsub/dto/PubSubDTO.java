package com.gewia.taskservice.pubsub.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a class as a pub/sub dto.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PubSubDTO {

	/**
	 * The topic this DTO belongs to.
	 * @return a topic
	 */
	String value();

}
