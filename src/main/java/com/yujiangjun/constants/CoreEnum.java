package com.yujiangjun.constants;

public class CoreEnum {

    public enum MessageType implements BaseEnum{

        TEXT(1,"TEXT"),
        IMAGE(2,"IMAGE"),
        VIDEO(3,"VIDEO")
        ;


        MessageType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private final int code;
        private final String value;

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String value() {
            return value;
        }
    }
    public enum MessageCat implements BaseEnum{

        USER_MES(1,"用户消息"),
        SYS_MES(2,"系统消息"),
        MES_ACK(3,"消息回执")
        ;


        MessageCat(int code, String value) {
            this.code = code;
            this.value = value;
        }

        private final int code;
        private final String value;

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String value() {
            return value;
        }
    }

}
