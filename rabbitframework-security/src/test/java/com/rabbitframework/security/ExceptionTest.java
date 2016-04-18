
package com.rabbitframework.security;
import com.rabbitframework.security.util.ClassUtils;
import junit.framework.TestCase;
import org.junit.Test;

/**
 */
public abstract class ExceptionTest extends TestCase {

	protected abstract Class<?> getExceptionClass();

	@Test
	public void testNoArgConstructor() {
		ClassUtils.newInstance(getExceptionClass());
	}

	@Test
	public void testMsgConstructor() throws Exception {
		ClassUtils.newInstance(getExceptionClass(), "Msg");
	}

	@Test
	public void testCauseConstructor() throws Exception {
		ClassUtils.newInstance(getExceptionClass(), new Throwable());
	}

	@Test
	public void testMsgCauseConstructor() {
		ClassUtils.newInstance(getExceptionClass(), "Msg", new Throwable());
	}
}
