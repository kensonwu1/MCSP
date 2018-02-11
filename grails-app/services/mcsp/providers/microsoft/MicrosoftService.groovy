package mcsp.providers.microsoft

import grails.gorm.transactions.Transactional
import mcsp.SubscriptionService
import mcsp.providers.microsoft.api.MicrosoftApiService
import mcsp.integrations.charon.api.CharonApiService
import mcsp.exceptions.SubscriptionNotExistException

@Transactional
class MicrosoftService {

    MicrosoftApiService microsoftApiService
    CharonApiService charonApiService
    SubscriptionService subscriptionService

    boolean auth() {
//        TODO
        return true
    }

    boolean isSupportedEvent(def event) {
        MicrosoftEventType eventType = getEventType(event)
        return (eventType != MicrosoftEventType.NotSupported)
    }

    MicrosoftEventType getEventType(def event) {
        try {
            MicrosoftEventType eventType = MicrosoftEventType.valueOf(event.eventType)
            return eventType
        } catch (Exception e) {
            return MicrosoftEventType.NotSupported
        }
    }

    def saveEvent(def event) {
        try {
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
            log.info "[MCSP] Save microsoft " + getEventType(event) + " event success."
        } catch (Exception e) {
            log.error "[MCSP] Save microsoft " + getEventType(event) + "event failed: " + e
        }
    }

    boolean handleEvent(def event) {
        try {
            MicrosoftEventType eventType = getEventType(event)
            if (eventType == MicrosoftEventType.Provision) {
                provision(event)
            } else if (eventType == MicrosoftEventType.Update) {
                update(event)
            } else if (eventType == MicrosoftEventType.Deprovision) {
                deprovision(event)
            } else {//NotSupported
                log.error "[MCSP] Not supported microsoft event."
                return false
            }
            log.info "[MCSP] Handle microsoft " + getEventType(event) + " event success."
            return true
        } catch (Exception e) {
            log.error "[MCSP] Handle microsoft " + getEventType(event) + " event failed: " + e
            return false
        }
    }

    void provision(def event) {
        String productId = event.product.productId
        String skuId = event.product.skuId
        String beneficiaryId = event.beneficiary.id
        String purchaserId = event.purchaser.id

        int quantity = microsoftApiService.getLicenseCount(productId, skuId, beneficiaryId)
        String subscriptionId = charonApiService.createSubscription(skuId, quantity)
        //TODO
        String productName = "Foglight"

        subscriptionService.pendingActivate(
                subscriptionId,
                productName,
                productId,
                skuId,
                beneficiaryId,
                purchaserId,
                quantity
        )
    }

    void update(def event) {
        String productId = event.product.productId
        String skuId = event.product.skuId
        String beneficiaryId = event.beneficiary.id
        String purchaserId = event.purchaser.id

        def existingSubscription = subscriptionService.getExistingSubscription(productId, skuId, beneficiaryId, purchaserId)
        if (!existingSubscription) {
            throw new SubscriptionNotExistException("[MCSP] No existing subscription for "+
                    "[productId:${productId},skuId:${skuId},beneficiaryId:${beneficiaryId},purchaserId:${purchaserId}]")
        }

        String subscriptionId = existingSubscription.subscriptionId
        int quantity = microsoftApiService.getLicenseCount(productId, skuId, beneficiaryId)

        charonApiService.updateSubscription(subscriptionId, quantity)

        subscriptionService.pendingUpdate(subscriptionId, quantity)
    }

    void deprovision(def event) {
        String productId = event.product.productId
        String skuId = event.product.skuId
        String beneficiaryId = event.beneficiary.id
        String purchaserId = event.purchaser.id

        def existingSubscription = subscriptionService.getExistingSubscription(productId, skuId, beneficiaryId, purchaserId)
        if (!existingSubscription) {
            throw new SubscriptionNotExistException("[MCSP] No existing subscription for "+
                    "[productId:${productId},skuId:${skuId},beneficiaryId:${beneficiaryId},purchaserId:${purchaserId}]")
        }

        String subscriptionId = existingSubscription.subscriptionId
        charonApiService.cancelSubscription(subscriptionId)

        subscriptionService.pendingCancel(subscriptionId)
    }

}
