package server.listener;

import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;
import server.model.AuditableData;

/**
 * User: kzimnick
 * Date: 26.08.12
 * Time: 09:49
 */
public class SaveOrUpdateDateListener extends DefaultSaveOrUpdateEventListener {

    @Override
    public void onSaveOrUpdate(SaveOrUpdateEvent event) {

        if (event.getObject() instanceof AuditableData) {
            AuditableData record = (AuditableData) event.getObject();
            record.onUpdate();
            if (record.getId() == null) {
                record.onCreate();
            }
        }
    }
}
