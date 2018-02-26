package mcsp.integrations.charon.api.mock

import grails.gorm.transactions.Transactional
import grails.plugins.rest.client.RestBuilder
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

@Transactional
class MockCharonApiService {

    static String TEST_SUBSCRIPTION_ID = "TestSubscriptionId"

    String createSubscription(String productRateplanId, int quantity) {
        String subscriptionId = TEST_SUBSCRIPTION_ID
        log.info "[Charon] Call mock charon api to create subscription: " + subscriptionId
        return subscriptionId
    }

    void updateSubscription(String subscriptionId, int quantity) {
        log.info "[Charon] Call mock charon api to update subscription: " + subscriptionId + " with quantity: " + quantity
    }

    void cancelSubscription(String subscriptionId) {
        log.info "[Charon] Call mock charon api to cancel subscription: " + subscriptionId
    }

    def getLicenseInfo(String subscriptionId){
        log.info "[Charon] Call mock charon api to get license info: " + subscriptionId
    }




}
