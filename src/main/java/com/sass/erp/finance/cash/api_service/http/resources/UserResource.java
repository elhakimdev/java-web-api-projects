package com.sass.erp.finance.cash.api_service.http.resources;

import com.sass.erp.finance.cash.api_service.models.entities.authorizations.UserEntity;

import java.util.HashMap;

public class UserResource {
    private final UserEntity user;

    public UserResource(UserEntity user){
        this.user = user;
    }

    public HashMap<String, Object> toResponse(){
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", this.user.getIdentifier().getUuid());
        responseMap.put("externalId", this.user.getExternalIdentifier().getExternalId());
        responseMap.put("systemRefId", this.user.getExternalIdentifier().getSystemRefId());
        responseMap.put("displayId", this.user.getExternalIdentifier().getDisplayId());
        responseMap.put("username", this.user.getUserUsername());
        responseMap.put("email", this.user.getUserEmail());
        responseMap.put("isActive", this.user.getUserIsActive());
        responseMap.put("lastLoggedInAt", this.user.getUserLastLoginAt());
        responseMap.put("emailVerifiedAt", this.user.getUserEmailVerifiedAt());
        responseMap.put("isLocked", this.user.getUserIsLocked());
        responseMap.put("isVerified", this.user.getUserIsVerified());
        responseMap.put("roles", this.user.getUserRoles());
        responseMap.put("createdAt", this.user.getTimeStamp().getCreatedAt());
        responseMap.put("createdBy", this.user.getAuditLog().getCreatedBy());
        responseMap.put("updatedAt", this.user.getTimeStamp().getUpdatedAt());
        responseMap.put("updatedBy", this.user.getAuditLog().getLastUpdatedBy());
        return responseMap;
    }
}
