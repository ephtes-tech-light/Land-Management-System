package com.land_management.user.service;

import com.land_management.user.dto.RegistrationDto;
import com.land_management.user.exception.UserAlreadyExistsException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final String realm="land-management";
    private final Keycloak keycloak;

    public String createKeycloakUser(RegistrationDto registrationDto){
        log.debug("create keycloak method called");
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(registrationDto.getEmail());
        userRepresentation.setUsername(registrationDto.getUsername());
        userRepresentation.setEnabled(true);

        CredentialRepresentation cred=new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(registrationDto.getPassword());
        cred.setTemporary(false);
        userRepresentation.setCredentials(Collections.singletonList(cred));
        log.debug("userRepresentation: {}", userRepresentation);

        Response response=keycloak.realm(realm).users().create(userRepresentation);
        log.debug("response user created: {}", response);

        int status = response.getStatus();
        log.debug("Keycloak create user response status: {}", status);
        if (status == Response.Status.CREATED.getStatusCode()) {
            String userId = CreatedResponseUtil.getCreatedId(response);
            log.debug("Keycloak user created with id: {}", userId);
            return userId;
        }

        // USER ALREADY EXISTS
        if (status == Response.Status.CONFLICT.getStatusCode()) {
            throw new UserAlreadyExistsException("User already exists in Keycloak");
        }

        // OTHER ERRORS
        String error = response.readEntity(String.class);
        throw new RuntimeException(
                "Keycloak user creation failed: " + status + " " + error
        );
    }
    public void rollbackUser(String userid){
        keycloak.realm(realm).users().get(userid).remove();
    }

}
