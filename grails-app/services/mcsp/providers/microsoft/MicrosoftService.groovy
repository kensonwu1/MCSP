package mcsp.providers.microsoft

import grails.gorm.transactions.Transactional
import mcsp.SubscriptionService
import mcsp.providers.microsoft.api.MicrosoftApiService
import mcsp.integrations.charon.api.CharonApiService

@Transactional
class MicrosoftService {

    MicrosoftApiService microsoftApiService
    CharonApiService charonApiService
    SubscriptionService subscriptionService

    boolean verifySignature() {
//        TODO
        return true
    }

    def saveEvent(def event) {
        String eventStr = event.toString()
        new MicrosoftEvent(
                event: eventStr,
                eventType: event.eventType,
                eventVersion: event.version,
                beneficiaryId: event.beneficiary.id,
                purchaserId: event.purchaser.id,
                productId: event.product.productId,
                skuId: event.product.skuId
        ).save()
    }

    boolean provision(def event) {
        try {
            String productId = event.product.productId
            String skuId = event.product.skuId
            String beneficiaryId = event.beneficiary.id
            String purchaserId = event.purchaser.id

            int quantity = microsoftApiService.getLicenseCount(productId, skuId, beneficiaryId)
            String subscriptionId = charonApiService.createSubscription(skuId, quantity)

            subscriptionService.save(
                    subscriptionId,
                    productId,
                    skuId,
                    beneficiaryId,
                    purchaserId,
                    quantity
            )

        } catch (Exception e) {
            log.error "Provision microsoft event failed: " + e
            return false
        }
        return true
    }

    boolean update(def event) {
        try {

        } catch (Exception e) {
            log.error "Update microsoft event failed: " + e
            return false
        }
        return true
    }

    boolean deprovision(def event) {
        try {

        } catch (Exception e) {
            log.error "Deprovision microsoft event failed: " + e
            return false
        }
        return true
    }

}
