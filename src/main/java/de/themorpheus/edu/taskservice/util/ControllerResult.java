package de.themorpheus.edu.taskservice.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerResult<T> {

	private T result;
	private Error error;

	private String extra;

	public Object getHttpResponse() {
		return this.isErrorPresent() ? this.getError().copyWithExtra(this.getExtra()) : this.getResult();
	}

	public boolean isResultPresent() {
		return this.getResult() != null;
	}

	public boolean isExtraPresent() {
		return this.getExtra() != null;
	}

	public boolean isErrorPresent() {
		return this.getError() != null;
	}

	public boolean isResultNotPresent() {
		return !this.isResultPresent();
	}

	public boolean isExtraNotPresent() {
		return !this.isExtraPresent();
	}

	public boolean isErrorNotPresent() {
		return !this.isErrorPresent();
	}

	public boolean isEmpty() {
		return this.getResult() == null && this.getError() == null && this.getExtra() == null;
	}

	public static <T> ControllerResult<T> empty() {
		return new ControllerResult<>();
	}

	public static <T> ControllerResult<T> of(T result) {
		return of(result, null);
	}

	public static <T> ControllerResult<T> of(T result, String extra) {
		ControllerResult<T> instance = new ControllerResult<>();
		instance.setResult(result);
		if (extra != null) instance.setExtra(extra);
		return instance;
	}

	public static <T> ControllerResult<T> of(Error error) {
		return of(error, null);
	}

	public static <T> ControllerResult<T> of(Error error, String extra) {
		ControllerResult<T> instance = new ControllerResult<>();
		instance.setError(error);
		if (extra != null) instance.setExtra(extra);
		return instance;
	}

	/**
	 * Maps the the given instance to an instance with other generics.
	 *
	 * @param <T> The new generics
	 * @param instance The instance to map
	 * @return the given instance
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static <T> ControllerResult<T> ret(ControllerResult instance) {
		if (instance.isErrorPresent()) throw new IllegalArgumentException("No error present in controller result");
		return instance;
	}

}
