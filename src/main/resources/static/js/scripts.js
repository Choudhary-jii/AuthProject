//document.addEventListener("DOMContentLoaded", function() {
//    const signupForm = document.getElementById('signup-form');
//    const loginForm = document.getElementById('login-form');
//
//    const signupContainer = document.getElementById('signup-container');
//    const loginContainer = document.getElementById('login-container');
//
//    // Show signup form by default
//    signupContainer.classList.add('active');
//
//    document.getElementById('switch-to-login').addEventListener('click', function() {
//        signupContainer.classList.remove('active');
//        loginContainer.classList.add('active');
//    });
//
//    document.getElementById('switch-to-signup').addEventListener('click', function() {
//        loginContainer.classList.remove('active');
//        signupContainer.classList.add('active');
//    });
//
//    signupForm.addEventListener('submit', function(event) {
//        event.preventDefault();
//        const username = document.getElementById('signup-username').value;
//        const password = document.getElementById('signup-password').value;
//
//        fetch('/auth/signup', {
//            method: 'POST',
//            headers: { 'Content-Type': 'application/json' },
//            body: JSON.stringify({ username, password })
//        })
//        .then(response => response.json())
//        .then(data => alert(data.message))
//        .catch(error => console.error('Error:', error));
//
//        // Reset form fields
//        signupForm.reset();
//    });
//
//    loginForm.addEventListener('submit', function(event) {
//        event.preventDefault();
//        const username = document.getElementById('login-username').value;
//        const password = document.getElementById('login-password').value;
//
//        fetch('/auth/login', {
//            method: 'POST',
//            headers: { 'Content-Type': 'application/json' },
//            body: JSON.stringify({ username, password })
//        })
//        .then(response => response.json())
//        .then(data => {
//            if (data.token) {
//                localStorage.setItem('token', data.token); // Store token in local storage
//                alert("Login successful!");
//                // Redirect to car management page or perform other actions
//            } else {
//                alert(data.message);
//            }
//        })
//        .catch(error => console.error('Error:', error));
//
//        // Reset form fields
//        loginForm.reset();
//    });
//});