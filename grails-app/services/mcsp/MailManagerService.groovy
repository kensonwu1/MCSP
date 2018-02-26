package mcsp

import grails.gorm.transactions.Transactional
import org.springframework.core.io.Resource
import mcsp.providers.microsoft.api.MicrosoftApiService

class MailManagerService {

    static transactional = false

    def assetResourceLocator
    def grailsApplication
    SubscriptionService subscriptionService
    def microsoftApiService

    def send(String emailFrom, String emailTo, List emailBcc, String emailSubject, String emailTemplate,
             def emailModel, def emailAttach) {

        Resource foglightLogo = assetResourceLocator.findAssetForURI('email/foglight.png')
        if (emailAttach != null) {
            sendMail {
                multipart true
                inline "foglightLogo", "image/png", foglightLogo
                from emailFrom
                to emailTo
                bcc emailBcc
                subject emailSubject
                html view: emailTemplate, model: emailModel
                attach emailAttach.fileName, emailAttach.contentType, emailAttach.bytes
            }
        } else {
            sendMail {
                multipart true
                inline "foglightLogo", "image/png", foglightLogo
                from emailFrom
                to emailTo
                bcc emailBcc
                subject emailSubject
                html view: emailTemplate, model: emailModel
            }
        }
    }

    def send(String emailTo, String emailSubject, String emailTemplate, def emailModel, def emailAttach) {
        def contactUsEmail = grailsApplication.config.getProperty('mail.contactUs', 'kenson.wu@quest.com')
        def emailFrom = grailsApplication.config.getProperty('mail.from', 'kenson.wu@quest.com')
        List emailBcc = grailsApplication.config.getProperty('mail.bcc', List.class)
        emailModel['contactUs'] = contactUsEmail
        send(emailFrom, emailTo, emailBcc, emailSubject, emailTemplate, emailModel, emailAttach)
    }

    def send(String emailTo, String emailSubject, String emailTemplate, def emailModel) {
        send(emailTo, emailSubject, emailTemplate, emailModel, null)
    }

    def createLicenseEmailAttach(String fileName, String content) {
        def emailAttach = [fileName: fileName, contentType: 'text/plain', bytes: content as byte[]]
        return emailAttach
    }

    def sendSubscriptionActivatedEmail(String subscriptionId, String licenseInfo) {
        try {
            def subsription = subscriptionService.getExistingSubscription(subscriptionId)

            String productName = subsription.productName
            int quantity = subsription.quantity

            String productId = subsription.productId
            String skuId = subsription.skuId
            String beneficiaryId = subsription.beneficiaryId
            def customer = microsoftApiService.getBeneficiary(productId, skuId, beneficiaryId)

            def emailAttach = createLicenseEmailAttach('QuestProduct.license', licenseInfo)

            send(customer.adminEmail,
                    'Subscription Activated',
                    '/emails/subscription_activated',
                    [customerName: customer.friendlyName,
                     productName : productName,
                     quantity    : quantity
                    ],
                    emailAttach)

            log.info "[MCSP] Send subscription activated email success."
        } catch (Exception e) {
            log.error "[MCSP] Send subscription activated email failed: " + e
        }
    }

    def sendSubscriptionUpdatedEmail(String subscriptionId, String licenseInfo) {
        try {
            def subsription = subscriptionService.getExistingSubscription(subscriptionId)

            String productName = subsription.productName
            int quantity = subsription.quantity

            String productId = subsription.productId
            String skuId = subsription.skuId
            String beneficiaryId = subsription.beneficiaryId
            def customer = microsoftApiService.getBeneficiary(productId, skuId, beneficiaryId)

            def emailAttach = createLicenseEmailAttach('QuestProduct.license', licenseInfo)

            send(customer.adminEmail,
                    'Subscription Updated',
                    '/emails/subscription_updated',
                    [customerName: customer.friendlyName,
                     productName : productName,
                     quantity    : quantity
                    ],
                    emailAttach)

            log.info "[MCSP] Send subscription updated email success."
        } catch (Exception e) {
            log.error "[MCSP] Send subscription updated email failed: " + e
        }
    }

    def sendSubscriptionCanceledEmail(String subscriptionId) {
        try {
            def subsription = subscriptionService.getExistingSubscription(subscriptionId)

            String productName = subsription.productName
            int quantity = subsription.quantity

            String productId = subsription.productId
            String skuId = subsription.skuId
            String beneficiaryId = subsription.beneficiaryId
            def customer = microsoftApiService.getBeneficiary(productId, skuId, beneficiaryId)

            send(customer.adminEmail,
                    'Subscription Cancelled',
                    '/emails/subscription_cancelled',
                    [customerName: customer.friendlyName,
                     productName : productName,
                     quantity    : quantity
                    ])

            log.info "[MCSP] Send subscription canceled email success."
        } catch (Exception e) {
            log.error "[MCSP] Send subscription canceled email failed: " + e
        }
    }
}
