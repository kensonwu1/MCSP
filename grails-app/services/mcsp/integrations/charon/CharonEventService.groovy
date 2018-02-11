package mcsp.integrations.charon

import grails.gorm.transactions.Transactional
import mcsp.SubscriptionService
import mcsp.MailManagerService

@Transactional
class CharonEventService {

    SubscriptionService subscriptionService
    MailManagerService mailManagerService

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
        String licenseInfo = getLicenseInfo(subscriptionId)
        mailManagerService.sendSubscriptionActivatedEmail(subscriptionId, licenseInfo)
    }

    void updateSubscription(def event){
        String subscriptionId = event.SubscriptionId
        subscriptionService.update(subscriptionId)
        String licenseInfo = getLicenseInfo(subscriptionId)
        mailManagerService.sendSubscriptionUpdatedEmail(subscriptionId, licenseInfo)
    }

    void cancelSubscription(def event){
        String subscriptionId = event.SubscriptionId
        subscriptionService.cancel(subscriptionId)
        mailManagerService.sendSubscriptionCanceledEmail(subscriptionId)
    }

    def getLicenseInfo(String subscriptionId) {
//        TODO call charon api
        String license = '''\
quest.license.foglight.feature.server.data_archiving=1
serial_number=123-45678
quest.license.foglight.feature.server.cartridge_installation=1
expiry=2030/01/01
quest.license.foglight.feature.server.request_trace_analysis=1
sitraka.license.signature=MC0CFAg5iGq84JZQ2FBioA4u7eUXYIO1AhUAhePAWyTFE6hQ6kZB/FQ4O+5cXYE=
quest.license.foglight.feature.server.agents_connection=1
quest.license.foglight.feature.server.config_management=1
quest.license.foglight.version=5.0
hosts=(unlimited)
quest.license.foglight.feature.server.ldap_integration=1
sitraka.license.version=4.2
quest.license.foglight.feature.server.performance_calendars=1
quest.license.foglight.feature.server.high_availability=1
product=Foglight
License Type=Perpetual
        '''
        return license
    }
}
