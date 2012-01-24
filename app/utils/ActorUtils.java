package utils;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import models.auditlog.AuditLogEvent;
import play.Logger;
import play.Play;
import play.classloading.ApplicationClasses.ApplicationClass;
import play.modules.auditlog.IActorProvider;
import sun.misc.Lock;


public class ActorUtils {
	static IActorProvider provider;
	static Lock lock = new Lock();

	public static IActorProvider getProvider() {
		synchronized (lock) {
			if (provider == null) {
				provider = loadProvider();
			}
		}
		return provider;
	}

	private static IActorProvider loadProvider() {
		List<ApplicationClass> assignableClasses = Play.classes.getAssignableClasses(IActorProvider.class);
		IActorProvider newInstance = null;
		try {
			if (assignableClasses != null && assignableClasses.size() > 0){
				Class<?> javaClass = assignableClasses.get(0).javaClass;
				newInstance = (IActorProvider) javaClass.newInstance();
			}
		} catch (Exception e) {
			Logger.error("Can't instanciate the IACtorProvider class ", e);
		}
		return newInstance;
	}

}
