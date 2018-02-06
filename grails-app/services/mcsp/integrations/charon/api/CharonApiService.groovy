package mcsp.integrations.charon.api

import grails.gorm.transactions.Transactional

import java.util.UUID
import grails.plugins.rest.client.RestBuilder
import org.springframework.util.MultiValueMap
import org.springframework.util.LinkedMultiValueMap

@Transactional
class CharonApiService {
    String CHARON_ID_SERVER = "https://id-qa.quest.com"
    String CHARON_CLIENT_ID = "dataprotection"
    String CHARON_CLIENT_SECRET = "3974c295-c04d-4719-aa7a-419f7b7cae48"

    String CHARON_API_SERVER = "https://api-qa.ondemand.quest.com"
    String CHARON_API = CHARON_API_SERVER + "/v3/api/"

    String MICROSOFT_ACCOUNT_ID = "TestMicrosoftZuoraAccountId"

    String createSubscription(String productRateplanId, int quantity) {
//        TODO
        try {
            String subscriptionId = "TestSubscriptionId"
            log.info "[Charon] Call charon api to create subscription: " + subscriptionId
            return subscriptionId
        } catch (Exception e) {
            log.error "[Charon] Call charon api to create subscription failed: " + e
            throw e
        }
    }

    void updateSubscription(String subscriptionId, int quantity) {
//        TODO
        try {
            log.info "[Charon] Call charon api to update subscription: " + subscriptionId + " with quantity: " + quantity
        } catch (Exception e) {
            log.error "[Charon] Call charon api to update subscription failed: " + e
            throw e
        }
    }

    void cancelSubscription(String subscriptionId) {
//        TODO
        try {
            log.info "[Charon] Call charon api to cancel subscription: " + subscriptionId
        } catch (Exception e) {
            log.error "[Charon] Call charon api to cancel subscription failed: " + e
            throw e
        }
    }

    String getAccessToken() {
        def url = CHARON_ID_SERVER + "/auth/realms/quest/protocol/openid-connect/token"
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()
        form.add("grant_type", "client_credentials")
        form.add("client_id", CHARON_CLIENT_ID)
        form.add("client_secret", CHARON_CLIENT_SECRET)
        RestBuilder rest = new RestBuilder()
        def response = rest.post(url) {
            contentType "application/x-www-form-urlencoded"
            body(form)
        }
        def responseJSON = response.json
        def accessToken = responseJSON.access_token
        log.debug "[Charon] Get Access Token: " + accessToken
        return accessToken
    }

    def getProduct(String skuId) {
        def accessToken = getAccessToken()
        def url = CHARON_API + "products/" + skuId
        log.info 'token: ' + accessToken
        log.info 'url: ' + url
        RestBuilder rest = new RestBuilder()
        def response = rest.get(url) {
            header 'Accept', 'application/json'
            header 'Authorization', 'Bearer ' + accessToken
        }
        def product = response.json
        return product
    }


}
