package kg.alatoo.demooauth.repos;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

public class CustomClientRegistrationRepo implements ClientRegistrationRepository {

    @Override
    public ClientRegistration findByRegistrationId(String registrationId) {

        return null;
    }
}
