package rw.ac.auca.kuzahealth.core.util;

import lombok.Getter;

@Getter
public enum EUnit {
    KG("kg"), G("g"), L("l"), PIECE("piece"), M("m");

    private final String description;

    private EUnit(String description) {
        this.description = description;
    }
}
