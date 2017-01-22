package com.student.room.web.common.velocity.parse;

import org.apache.velocity.util.introspection.AbstractChainableUberspector;
import org.apache.velocity.util.introspection.Info;
import org.apache.velocity.util.introspection.VelPropertyGet;

/**
 * $request.abc --> $request.getAttibute('abc')
 * 
 * @author maomh
 *
 */
public class WebUberspector extends AbstractChainableUberspector {
	@Override
	public VelPropertyGet getPropertyGet(Object obj, String identifier, Info i) throws Exception {
		VelPropertyGet vpg = super.getPropertyGet(obj, identifier, i);
		if (vpg == null) {
			WebGetAttributeExecutor executor = new WebGetAttributeExecutor(log, obj.getClass(), identifier);
			if (executor.isAlive()) {
				vpg =new VelGetterImpl(executor);
			}
		}
		return vpg;
	}
	
}
