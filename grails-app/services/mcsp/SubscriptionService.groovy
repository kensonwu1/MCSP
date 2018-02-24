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

    def getSubscriptionStatus(String subscriptionId) {
        Subscription subscription = getExistingSubscription(subscriptionId)
        return subscription.status
    }

    def getSubscriptionStatus(String productId, String skuId, String beneficiaryId, String purchaserId) {
        Subscription subscription = getExistingSubscription(productId, skuId, beneficiaryId, purchaserId)
        return subscription.status
    }
}
