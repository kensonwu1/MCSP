import delay from './delay';

// This file mocks a web API by working with the hard-coded data below.
// It uses setTimeout to simulate the delay of an AJAX call.
// All calls return promises.
const rateplans = [
  {
    id: "react-flux-building-applications",
    name: "Zuora rateplan 1",
    watchHref: "http://www.pluralsight.com/courses/react-flux-building-applications",
    authorId: "cory-house",
    length: "5:08",
    category: "JavaScript"
  },
  {
    id: "clean-code",
    name: "Zuora rateplan 2",
    watchHref: "http://www.pluralsight.com/courses/writing-clean-code-humans",
    authorId: "cory-house",
    length: "3:10",
    category: "Software Practices"
  },
  {
    id: "architecture",
    name: "Zuora rateplan 3",
    watchHref: "http://www.pluralsight.com/courses/architecting-applications-dotnet",
    authorId: "cory-house",
    length: "2:52",
    category: "Software Architecture"
  },
  {
    id: "career-reboot-for-developer-mind",
    name: "Zuora rateplan 4",
    watchHref: "http://www.pluralsight.com/courses/career-reboot-for-developer-mind",
    authorId: "cory-house",
    length: "2:30",
    category: "Career"
  },
  {
    id: "web-components-shadow-dom",
    name: "Zuora rateplan 5",
    watchHref: "http://www.pluralsight.com/courses/web-components-shadow-dom",
    authorId: "cory-house",
    length: "5:10",
    category: "HTML5"
  }
];



class SubscriptionApi {
  static getAllRateplans() {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        resolve(Object.assign([], rateplans));
      }, delay);
    });
  }

  static getSignature() {
    return new Promise((resolve, reject) => {
          setTimeout(() => {
            resolve("TEST");
          }, delay);
    });
  }

  
}

export default SubscriptionApi;
