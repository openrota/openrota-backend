package com.shareNwork.domain.constants;

public enum EmailType {

    OPENROTA_INVITATION("openrotaInvitation"),
    INVITATION_EXPIRATION("invitationExpiration"),
    NEW_ACCESS_REQ("newAccessReq"),
    ACCESS_REQ_STATUS("accessReqStatus"),
    NEW_RESOURCE_REQ("newResourceReq"),
    RESOURCE_REQUEST_STATUS("resourceRequestStatus"),
    PROJECT_CLOSURE_DUE_REMINDER("projectClosureDueReminder"),
    PROJECT_CLOSURE_REMINDER("projectClosureReminder"),
    PROJECT_COMPLETED("projectCompleted");

    private final String value;
    EmailType(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
