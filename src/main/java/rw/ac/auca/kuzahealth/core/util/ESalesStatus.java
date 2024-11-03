package rw.ac.auca.kuzahealth.core.util;

import lombok.Getter;

@Getter
public enum ESalesStatus {
    INITIATED("Initiated"), PROCESSED("Processed"), PAID("Paid"), RETURNED("Returned");

    private final String description;

    private ESalesStatus(String description) {
        this.description = description;
    }
}
