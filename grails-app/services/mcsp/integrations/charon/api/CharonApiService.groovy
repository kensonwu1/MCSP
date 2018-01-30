package mcsp.integrations.charon.api

import grails.gorm.transactions.Transactional
import java.util.UUID

@Transactional
class CharonApiService {
    String MICROSOFT_ACCOUNT_ID = "TestMicrosoftZuoraAccountId"

    String createSubscription(String productRateplanId, int quantity) {
//        TODO
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        String subscriptionId = "TestSubscriptionId" + randomUUIDString
        log.info "Call charon api to create subsription: " + subscriptionId
        return subscriptionId
    }
}
