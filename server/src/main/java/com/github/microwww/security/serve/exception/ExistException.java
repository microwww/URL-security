package com.github.microwww.security.serve.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

public abstract class ExistException {
    public static final String EXIST_I18N_PREFIX = "exist.resource.class.";
    public static final String NOT_EXIST_I18N_PREFIX = "not.find.resource.class.";

    public static class Exist extends ServiceException {

        private static final long serialVersionUID = 1L;

        public Exist(Object data) {
            this(data, "-");
        }

        public Exist(Object data, String message) {
            super(data, "Resources exist ( %s ) %s", data.getClass(), message);
        }

        @Override
        public String i18n(MessageSource messageSource) {
            String name = ((Class) getData()[0]).getSimpleName();
            String key = EXIST_I18N_PREFIX + name;
            try {
                return this.i18n(messageSource, key, this.getData());
            } catch (NoSuchMessageException e) {
                return "NOT config i18n KEY : " + key;
            }
        }
    }

    public static class NotExist extends ServiceException {

        private static final long serialVersionUID = 1L;

        public NotExist(Class resource) {
            this(resource, "-");
        }

        public NotExist(Class resource, String message) {
            super(resource, "RESOURCES not exist ( %s ) %s", resource.getName(), message == null ? "-" : message);
        }

        @Override
        public String i18n(MessageSource messageSource) {
            String name = ((Class) getData()[0]).getSimpleName();
            String key = NOT_EXIST_I18N_PREFIX + name;
            try {
                return this.i18n(messageSource, key, this.getData());
            } catch (NoSuchMessageException e) {
                return "Not config i18n key : " + key;
            }
        }
    }
}
