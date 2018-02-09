package mcsp

import grails.gorm.transactions.Transactional
import org.springframework.core.io.Resource

@Transactional
class MailManagerService {

    def assetResourceLocator
    def grailsApplication

    def send(String emailFrom, String emailTo,
             def emailBcc, String emailSubject, String emailTemplate, def emailModel) {
        Resource foglightLogo = assetResourceLocator.findAssetForURI('email/foglight.png')
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

    def send(String emailTo, String emailSubject, String emailTemplate, def emailModel) {
        def contactUsEmail = grailsApplication.config.getProperty('mail.contactUs', 'kenson.wu@quest.com')
        def emailFrom = grailsApplication.config.getProperty('mail.from', 'kenson.wu@quest.com')
        def emailBcc = grailsApplication.config.getProperty('mail.bcc')
        emailModel['contactUs'] = contactUsEmail
        send(emailFrom, emailTo, emailBcc, emailSubject, emailTemplate, emailModel)
    }

    def sendSubscriptionActivatedEmail() {
        try {
            log.info "[MCSP] Send subscription activated email success."
        } catch (Exception e) {
            log.error "[MCSP] Send subscription activated email failed: " + e
        }
    }

    def sendSubscriptionUpdatedEmail() {
        try {
            log.info "[MCSP] Send subscription updated email success."
        } catch (Exception e) {
            log.error "[MCSP] Send subscription updated email failed: " + e
        }
    }

    def sendSubscriptionCanceledEmail() {
        try {
            log.info "[MCSP] Send subscription canceled email success."
        } catch (Exception e) {
            log.error "[MCSP] Send subscription canceled email failed: " + e
        }
    }
}
