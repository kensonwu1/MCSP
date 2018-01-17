package mcsp

class HelloController {

    def index() {
        render "This is a Grails project with external Azure Mysql Database"
    }

    def save(){
        new Book(title:"The Stand").save()
        render "Saved"
    }

    def show(){
        Book book = Book.get(1)
        String title = book.title
        render "Title: "+title
    }
}
