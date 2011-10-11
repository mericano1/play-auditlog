package play.modules.auditlog;

import play.Logger;
import play.Play;
import play.utils.Java;

import java.util.List;

public class AuditLog {

    public static Object invoke(String m, Object... args) {
        Class auditLog = null;
        try {
            auditLog = Play.classloader.loadClass("controllers.auditlog.DefaultAuditLogEvents");
        } catch (ClassNotFoundException e) {
            Logger.error("class controllers.auditlog.DefaultAuditLogEvents not found");
            return null;
        }
        List<Class> classes = Play.classloader.getAssignableClasses(auditLog);
        if (classes.size() > 0) {
            auditLog = classes.get(0);
        }
        try {
            return Java.invokeStaticOrParent(auditLog, m, args);
        } catch (Exception e) {
            Logger.error(e, "Unable to invoke AuditLog method");
            return null;
        }
    }

}
