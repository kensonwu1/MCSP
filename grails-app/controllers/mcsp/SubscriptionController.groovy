package mcsp

import grails.converters.JSON

class SubscriptionController {

    MailManagerService mailManagerService
    SubscriptionService subscriptionService

    def index() {
        render "test"
    }

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

    def testEmail(){
        String licenseInfo = subscriptionService.getLicenseInfo('test')
//        def emailAttach = mailManagerService.createLicenseEmailAttach('Foglight.license',licenseInfo)
//        mailManagerService.send('kenson.wu@quest.com','Kenson Test','/emails/test',[hello:'This is Kenson Test'],emailAttach)
        render 'sent'
    }

}
