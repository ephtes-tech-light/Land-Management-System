package com.land_management.user.service;

import com.land_management.user.dto.RegistrationDto;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakService {
    private final String realm="land-management";
    private final Keycloak keycloak;

    public String createKeycloakUser(RegistrationDto registrationDto){
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(registrationDto.getEmail());
        userRepresentation.setUsername(registrationDto.getUsername());
        userRepresentation.setEnabled(true);

        CredentialRepresentation cred=new CredentialRepresentation();
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(registrationDto.getPassword());
        cred.setTemporary(false);
        userRepresentation.setCredentials(Collections.singletonList(cred));

        Response response=keycloak.realm(realm).users().create(userRepresentation);
        if (response.getStatus() != 201) {
            return CreatedResponseUtil.getCreatedId(response);
        }
        else {
            throw new RuntimeException("Keycloak User creation failed"+response.getStatus());
        }

    }
    public void rollbackUser(String userid){
        keycloak.realm(realm).users().get(userid).remove();
    }

}
