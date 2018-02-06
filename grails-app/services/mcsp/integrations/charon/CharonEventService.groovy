package mcsp.integrations.charon

import grails.gorm.transactions.Transactional
import mcsp.SubscriptionService

@Transactional
class CharonEventService {

    SubscriptionService subscriptionService

    boolean auth() {
//        TODO
        return true
    }

    boolean isSupportedEvent(def event){
        CharonEventType eventType = getEventType(event)
        return (eventType != CharonEventType.NotSupported)
    }

    CharonEventType getEventType(def event) {
        try {
            CharonEventType eventType = CharonEventType.valueOf(event.EventType+'_'+event.SubType)
            return eventType
        } catch (Exception e) {
            return CharonEventType.NotSupported
        }
    }

    def saveEvent(def event){
        try {
            String eventStr = event.toString()
            new CharonEvent(
                    event:eventStr,
                    eventType: event.EventType,
                    subType: event.SubType,
                    subscriptionId: event.SubscriptionId,
                    subscriptionName: event.SubscriptionName,
                    productSKU: event.ProductSKU,
                    productRatePlanId: event.ProductRatePlanId,
                    accountId: event.AccountId
            ).save()
            log.info "[MCSP] Save charon " + getEventType(event) + " event success."
        } catch (Exception e) {
            log.error "[MCSP] Save charon " + getEventType(event) + "event failed: " + e
        }
    }

    boolean handleEvent(def event){
        try{
            CharonEventType eventType = getEventType(event)
            if (eventType == CharonEventType.SubscriptionCreated_Activated) {
                activateSubscription(event)
            } else if (eventType == CharonEventType.AmendmentProcessed_UpdateProduct) {
                updateSubscription(event)
            } else if (eventType == CharonEventType.AmendmentProcessed_Cancellation) {
                cancelSubscription(event)
            }else{//NotSupported
                log.error "[MCSP] Not supported charon event."
                return false
            }
            log.info "[MCSP] Handle charon " + getEventType(event) + " event success."
            return true
        }catch (Exception e){
            log.error "[MCSP] Handle charon " + getEventType(event) + " event failed: " + e
            return false
        }
    }

    void activateSubscription(def event){
        String subscriptionId = event.SubscriptionId
        subscriptionService.activate(subscriptionId)
        //TODO: sending email to customer
    }

    void updateSubscription(def event){
        String subscriptionId = event.SubscriptionId
        subscriptionService.update(subscriptionId)
        //TODO: sending email to customer
    }

    void cancelSubscription(def event){
        String subscriptionId = event.SubscriptionId
        subscriptionService.cancel(subscriptionId)
        //TODO: sending email to customer
    }
}
