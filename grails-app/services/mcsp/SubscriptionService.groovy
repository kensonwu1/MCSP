package mcsp

import grails.gorm.transactions.Transactional

@Transactional
class SubscriptionService {

    def save(String subscriptionId, String productId, String skuId, String beneficiaryId, String purchaserId, int quantity) {
        new Subscription(
                subscriptionId: subscriptionId,
                productId: productId,
                skuId: skuId,
                quantity: quantity,
                beneficiaryId: beneficiaryId,
                purchaserId: purchaserId,
                status: SubscriptionStatus.Pending
        ).save()
    }
}
