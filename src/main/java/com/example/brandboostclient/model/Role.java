package com.example.brandboostclient.model;

public enum Role {
        USER("Пользователь"),
        ADMIN("Администратор");

        private final String name;

        Role(String name) {
            this.name = name;
        }

        public String getName(){
            return name;
        }

    @Override
    public String toString() {
        return name;
    }
}
