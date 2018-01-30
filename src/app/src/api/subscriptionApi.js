class SubscriptionApi {
  static getAllSubscriptions() {
     return fetch('/subscription/list').then(function(res) {
        return res.json();
    }).then(function(json) {
        return json;
    });
  }
}

export default SubscriptionApi;
