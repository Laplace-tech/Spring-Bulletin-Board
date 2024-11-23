package com.anna.sbb.viewcontroller;

import java.security.Principal;
import java.util.function.BiFunction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControllerUtils {

	public static boolean hasPermission(Long resourceId, Principal principal, BiFunction<Long, Principal, Boolean> permissionChecker) {
		boolean hasPermission = permissionChecker.apply(resourceId, principal);
		if (!hasPermission) {
			log.warn("[ControllerUtils] : User [{}] does not have permission for resource [{}]", principal.getName(), resourceId);
		}
		return hasPermission;
	}

}
