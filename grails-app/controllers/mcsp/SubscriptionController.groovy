package mcsp

import grails.converters.JSON

class SubscriptionController {

    def index() { }

    def list(){
        def subscriptions = Subscription.list()
        def results = []
        subscriptions.each {subscription->
            def subscriptionMap = [
                    "subscriptionId": subscription.subscriptionId,
                    "productId":subscription.productId,
                    "skuId":subscription.skuId,
                    "quantity":subscription.quantity,
                    "beneficiaryId":subscription.beneficiaryId,
                    "purchaserId":subscription.purchaserId,
                    "dateCreated":subscription.dateCreated,
                    "status":subscription.status,
            ]
            results << subscriptionMap
        }
        render results as JSON
    }
}
