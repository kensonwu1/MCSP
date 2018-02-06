package mcsp.exceptions

class SubscriptionNotExistException extends RuntimeException {
    public SubscriptionNotExistException() {
        super()
    }

    public SubscriptionNotExistException(String message) {
        super(message)
    }

    public SubscriptionNotExistException(String message, Throwable cause) {
        super(message, cause)

    }

    public SubscriptionNotExistException(Throwable cause) {
        super(cause)

    }
}


