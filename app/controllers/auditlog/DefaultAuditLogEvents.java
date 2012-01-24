package controllers.auditlog;

import jobs.auditlog.SaveAuditLogEvent;
import play.modules.auditlog.Auditable.Operation;
import play.modules.auditlog.IActorProvider;
import play.mvc.Controller;
import play.mvc.Scope.Session;
import utils.ActorUtils;

public class DefaultAuditLogEvents {

    private static String getActor() {
    	IActorProvider provider = ActorUtils.getProvider();
    	if (provider == null) throw new RuntimeException("You need to implement the interface IActorProvider to tell me how to get the actor");
		return provider.getActor();
    }

    static void onCreate(String model, Long modelId) {
        String actor = getActor();
        new SaveAuditLogEvent(
                model,
                modelId,
                Operation.CREATE,
                null,
                null,
                null,
                actor
        ).now();
    }

    static void onUpdate(String model, Long modelId, String property, String oldValue, String value) {
    	String actor = getActor();
        new SaveAuditLogEvent(
                model,
                modelId,
                Operation.UPDATE,
                property,
                oldValue,
                value,
                actor
        ).now();
    }

    static void onDelete(String model, Long modelId) {
    	String actor = getActor();
        new SaveAuditLogEvent(
                model,
                modelId,
                Operation.DELETE,
                null,
                null,
                null,
                actor
        ).now();
    }

}
