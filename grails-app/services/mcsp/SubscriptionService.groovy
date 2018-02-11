package mcsp

import grails.gorm.transactions.Transactional

@Transactional
class SubscriptionService {

    def pendingActivate(String subscriptionId, String productName, String productId, String skuId, String beneficiaryId, String purchaserId, int quantity) {
        new Subscription(
                subscriptionId: subscriptionId,
                productName: productName,
                productId: productId,
                skuId: skuId,
                quantity: quantity,
                beneficiaryId: beneficiaryId,
                purchaserId: purchaserId,
                statusCategory: SubscriptionStatusCategory.SubscriptionCreated,
                status: SubscriptionStatus.PendingActivation
        ).save()
    }

    def pendingUpdate(String subscriptionId, int quantity) {
        def subscription = Subscription.findBySubscriptionId(subscriptionId)
        subscription.quantity = quantity
        subscription.statusCategory = SubscriptionStatusCategory.AmendmentProcessed
        subscription.status = SubscriptionStatus.PendingUpdateProduct
        subscription.save()
    }

    def pendingCancel(String subscriptionId) {
        def subscription = Subscription.findBySubscriptionId(subscriptionId)
        subscription.statusCategory = SubscriptionStatusCategory.AmendmentProcessed
        subscription.status = SubscriptionStatus.PendingCancellation
        subscription.save()
    }

    def activate(String subscriptionId) {
        def subscription = Subscription.findBySubscriptionId(subscriptionId)
        subscription.statusCategory = SubscriptionStatusCategory.SubscriptionCreated
        subscription.status = SubscriptionStatus.Activated
        subscription.save()
    }

    def update(String subscriptionId) {
        def subscription = Subscription.findBySubscriptionId(subscriptionId)
        subscription.statusCategory = SubscriptionStatusCategory.AmendmentProcessed
        subscription.status = SubscriptionStatus.Updated
        subscription.save()
    }

    def cancel(String subscriptionId) {
        def subscription = Subscription.findBySubscriptionId(subscriptionId)
        subscription.statusCategory = SubscriptionStatusCategory.AmendmentProcessed
        subscription.status = SubscriptionStatus.Cancelled
        subscription.save()
    }

    def getExistingSubscription(String productId, String skuId, String beneficiaryId, String purchaserId) {
        return Subscription.findWhere(
                productId: productId,
                skuId: skuId,
                beneficiaryId: beneficiaryId,
                purchaserId: purchaserId
        )
    }

    def getExistingSubscription(String subscriptionId) {
        return Subscription.findBySubscriptionId(subscriptionId)
    }

    def getLicenseInfo(String subscriptionId) {
//        TODO
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
