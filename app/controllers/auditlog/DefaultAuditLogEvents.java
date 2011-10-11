package controllers.auditlog;

import jobs.auditlog.SaveAuditLogEvent;
import play.modules.auditlog.Auditable.Operation;
import play.mvc.Controller;
import play.mvc.Scope.Session;

public class DefaultAuditLogEvents {

    private static String getActor() {
    	return Session.current() != null ? Session.current().get("username") : "anonymous";
    }

    static void onCreate(String model, String modelId) {
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

    static void onUpdate(String model, String modelId, String property, String oldValue, String value) {
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

    static void onDelete(String model, String modelId) {
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
