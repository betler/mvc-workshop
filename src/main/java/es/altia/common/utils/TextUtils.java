/**
 *
 */
package es.altia.common.utils;

import org.springframework.stereotype.Component;

/**
 * @author mikel
 *
 */
@Component
public class TextUtils implements ITextUtils {

	@Override
	public String toUpper(final String text) {
		return text.toUpperCase();
	}

}
