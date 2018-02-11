grails {
    mail {
        host = "relay.prod.quest.corp"
        port = 25
        props = ["mail.smtp.auth":"false"]
    }
}
mail.from='kenson.wu@quest.com'
mail.contactUs='kenson.wu@quset.com'
mail.bcc=['kenson.wu@quest.com']

charon.identity.server='https://id-qa.quest.com'
charon.identity.client.id='dataprotection'
charon.identity.client.secret='3974c295-c04d-4719-aa7a-419f7b7cae48'
charon.api.server='https://api-qa.ondemand.quest.com'
charon.api.version='v3'