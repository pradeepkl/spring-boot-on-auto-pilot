package com.ekart.config;

import java.time.Instant;

import org.hibernate.Interceptor;
import org.hibernate.type.Type;

public class EkartAuditInterceptor implements Interceptor {

    @Override
    public boolean onSave(Object entity, Object id, Object[] state,
            String[] propertyNames, Type[] types) {
        setAuditFields(state, propertyNames, true);
        return true;
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id,
            Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) {
        setAuditFields(currentState, propertyNames, false);
        return true;
    }

    private void setAuditFields(Object[] state, String[] propertyNames,
            boolean isNew) {
        Instant now = Instant.now();
        String actor = "system";
        for (int i = 0; i < propertyNames.length; i++) {
            switch (propertyNames[i]) {
                case "createdTimestamp" -> {
                    if (isNew) {
                        state[i] = now;
                    }
                }
                case "updatedTimestamp" -> state[i] = now;
                case "createdBy" -> {
                    if (isNew) {
                        state[i] = actor;
                    }
                }
                case "updatedBy" -> state[i] = actor;
                default -> {
                }
            }
        }
    }
}
