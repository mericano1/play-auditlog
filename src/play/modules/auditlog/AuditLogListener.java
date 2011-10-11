package play.modules.auditlog;

import java.util.Arrays;

import org.hibernate.event.PostDeleteEvent;
import org.hibernate.event.PostDeleteEventListener;
import org.hibernate.event.PostInsertEvent;
import org.hibernate.event.PostInsertEventListener;
import org.hibernate.event.PostUpdateEvent;
import org.hibernate.event.PostUpdateEventListener;

import play.db.jpa.Model;
import play.modules.auditlog.Auditable.Operation;
import play.mvc.Scope.Session;

public class AuditLogListener implements PostInsertEventListener, PostUpdateEventListener, PostDeleteEventListener {

    public void onPostInsert(PostInsertEvent event) {
    	Model entity = (Model) event.getEntity();
    	if (hasAnnotation(entity.getClass(),Operation.CREATE)) {
            String model = entity.getClass().getName();
            String modelId = entity._key().toString();
            AuditLog.invoke("onCreate",model,modelId);
        }
    }

    public void onPostUpdate(PostUpdateEvent event) {
        Model entity = (Model) event.getEntity();
        if (hasAnnotation(entity.getClass(),Operation.UPDATE)) {
            String model = entity.getClass().getName();
            String modelId = entity._key().toString();
            String[] properties = event.getPersister().getPropertyNames();
            Object[] oldValues = event.getOldState();
            Object[] values = event.getState();
            for (int i=0; i<properties.length; i++) {
                boolean updated = false;
                if (oldValues[i] == null) {
                    if (values[i] != null) {
                        updated = true;
                    }
                } else if (!oldValues[i].equals(values[i])) {
                    updated = true;
                }
                if (updated) {
                    AuditLog.invoke("onUpdate",model,modelId,properties[i],
                    		oldValues[i] == null ? "NULL" : oldValues[i].toString(),
                            values[i] == null ? "NULL" : values[i].toString());
                }
            }
        }
    }

    public void onPostDelete(PostDeleteEvent event) {
    	Model entity = (Model) event.getEntity();
        if (hasAnnotation(entity.getClass(),Operation.DELETE)) {
            String model = entity.getClass().getName();
            String modelId = entity._key().toString();
            AuditLog.invoke("onDelete",model,modelId);
        }
    }
    
    
    /**
     * Checks the annotations on the class
     * @param clazz
     * @param type
     * @return
     */
    private boolean hasAnnotation(Class <? extends Model> clazz, Operation type){
		if (clazz.isAnnotationPresent(Auditable.class)) {
			Auditable annotation = clazz.getAnnotation(Auditable.class);
			return Arrays.asList(annotation.recordOn()).contains(type);
		}
		return false;
	}

}
