(function () {
  "use strict";

  let forms = document.querySelectorAll('.php-email-form');

  forms.forEach( function(e) {
    e.addEventListener('submit', function(event) {
      event.preventDefault();

      let thisForm = this;

      thisForm.querySelector('.loading').classList.add('d-block');
      thisForm.querySelector('.error-message').classList.remove('d-block');
      thisForm.querySelector('.sent-message').classList.remove('d-block');

      let formData = new FormData( thisForm );

      emailjs.sendForm('service_cgc1mjt', 'template_w08p4j4', thisForm, 'bZcb4L8vp9i0UMQ9b')
        .then((result) => {
          thisForm.querySelector('.loading').classList.remove('d-block');
          if (result.text === 'OK') {
            thisForm.querySelector('.sent-message').classList.add('d-block');
            thisForm.reset();
            setTimeout(function() {
              thisForm.querySelector('.sent-message').classList.remove('d-block');
            }, 5000);
          } else {
            throw new Error(result.text);
          }
        })
        .catch((error) => {
          displayError(thisForm, error);
        });
    });
  });

  function displayError(thisForm, error) {
    thisForm.querySelector('.loading').classList.remove('d-block');
    thisForm.querySelector('.error-message').innerHTML = error;
    thisForm.querySelector('.error-message').classList.add('d-block');
    setTimeout(function() {
      thisForm.querySelector('.error-message').classList.remove('d-block');
    }, 5000);
  }
  window.addEventListener('resize', function() {
    var width = window.innerWidth;
    var headings = document.getElementsByClassName('heading');
    if (width <= 600) {
      for (var i = 0; i < headings.length; i++) {
        var h4 = document.createElement('h6');
        h4.innerHTML = headings[i].innerHTML;
        h4.className = 'heading'; // add the class to the new element
        headings[i].parentNode.replaceChild(h4, headings[i]);
      }
    } else {
      var headings = document.getElementsByClassName('heading');
      for (var i = 0; i < headings.length; i++) {
        var h2 = document.createElement('h2');
        h2.innerHTML = headings[i].innerHTML;
        h2.className = 'heading'; // add the class to the new element
        headings[i].parentNode.replaceChild(h2, headings[i]);
      }
    }
  });

})();
