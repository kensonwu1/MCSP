package mcsp

class Subscription {
    String subscriptionId
    String productId
    String skuId
    String beneficiaryId
    String purchaserId
    int quantity
    SubscriptionStatus status

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
}