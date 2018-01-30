package mcsp

class UrlMappings {

    static mappings = {
        "/cloud/providers/microsoft/event/notify"{
            controller="microsoftEvent"
        }
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        group "/app", {
            "**"(uri:"/index.html")
        }

        "/"(uri:"/app")
//        "/"(view:"/index")

        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
