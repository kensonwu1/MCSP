package mcsp.integrations.charon.api

import grails.gorm.transactions.Transactional

import java.util.UUID
import grails.plugins.rest.client.RestBuilder
import org.springframework.util.MultiValueMap
import org.springframework.util.LinkedMultiValueMap

@Transactional
class CharonApiService {
    def grailsApplication

    String MICROSOFT_ACCOUNT_ID = "TestMicrosoftZuoraAccountId"

    String getCharonIdentityURL(){
        return "${grailsApplication.config.getProperty('charon.identity.server')}/auth/realms/quest/protocol/openid-connect/token"
    }

    String getCharonIdentityClientID(){
        return grailsApplication.config.getProperty('charon.identity.client.id')
    }

    String getCharonIdentityClientSecret(){
        return grailsApplication.config.getProperty('charon.identity.client.secret')
    }

    String getCharonAPI(){
        return "${grailsApplication.config.getProperty('charon.api.server')}/${grailsApplication.config.getProperty('charon.api.version')}/api"
    }

    String getAccessToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()
        form.add("grant_type", "client_credentials")
        form.add("client_id", charonIdentityClientID)
        form.add("client_secret", charonIdentityClientSecret)
        RestBuilder rest = new RestBuilder()
        def response = rest.post(charonIdentityURL) {
            contentType "application/x-www-form-urlencoded"
            body(form)
        }
        def responseJSON = response.json
        def accessToken = responseJSON.access_token
        log.debug "[Charon] Get Access Token: " + accessToken
        return accessToken
    }

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

    def getProduct(String skuId) {
        def accessToken = getAccessToken()
        def url = "${charonAPI}/products/${skuId}"
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
