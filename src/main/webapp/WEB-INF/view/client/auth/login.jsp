<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Login</title>
                <link rel="stylesheet" href="/client/css/register.css">

            </head>

            <body>
                <div class="container">
                    <h1 class="title">Welcome Back</h1>

                    <form id="loginForm" method="post" action="/login">
                        <c:if test="${param.error != null}">
                            <div class="my-2" style="color: red;">Invalid email or password.</div>
                        </c:if>
                        <c:if test="${param.logout != null}">
                            <div class="my-2" style="color: rgb(43, 255, 0);">You have been logged out.</div>
                        </c:if>
                        <div class="form-group">
                            <input type="email" id="email" placeholder="Email address" name="username" required>
                            <div class="error-message" id="emailError"></div>
                        </div>

                        <div class="form-group">
                            <input type="password" id="password" placeholder="Password" name="password" required>
                            <div class="error-message" id="passwordError"></div>
                        </div>

                        <div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        </div>

                        <div class="forgot-password">
                            <a href="#" id="forgotPasswordLink">Forgot password?</a>
                        </div>

                        <button type="submit" class="btn">Sign In</button>
                    </form>

                    <div class="divider">
                        <span>or</span>
                    </div>

                    <div class="register-link">
                        Don't have an account? <a href="register.html">Create account</a>
                    </div>
                </div>

                <!-- <script>
        document.addEventListener('DOMContentLoaded', function () {
            const form = document.getElementById('loginForm');
            const email = document.getElementById('email');
            const password = document.getElementById('password');
            const forgotPasswordLink = document.getElementById('forgotPasswordLink');

            // Clear error messages
            function clearErrors() {
                document.querySelectorAll('.error-message').forEach(error => {
                    error.style.display = 'none';
                    error.textContent = '';
                });
            }

            // Show error message
            function showError(fieldId, message) {
                const errorElement = document.getElementById(fieldId + 'Error');
                errorElement.textContent = message;
                errorElement.style.display = 'block';
            }

            // Validate email format
            function isValidEmail(email) {
                const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                return emailRegex.test(email);
            }

            // Form submission
            form.addEventListener('submit', function (e) {
                e.preventDefault();
                clearErrors();

                let isValid = true;

                // Validate email
                if (!email.value.trim()) {
                    showError('email', 'Email is required');
                    isValid = false;
                } else if (!isValidEmail(email.value)) {
                    showError('email', 'Please enter a valid email address');
                    isValid = false;
                }

                // Validate password
                if (!password.value) {
                    showError('password', 'Password is required');
                    isValid = false;
                }

                if (isValid) {
                    // Simulate login
                    const loginData = {
                        email: email.value.trim(),
                        password: password.value
                    };

                    console.log('Logging in with data:', loginData);

                    // Simulate API call
                    setTimeout(() => {
                        // Check for demo credentials
                        if (email.value === 'demo@example.com' && password.value === 'password') {
                            alert('Login successful! Welcome back!');
                            // Redirect to dashboard or home page
                            console.log('Redirecting to dashboard...');
                        } else {
                            showError('password', 'Invalid email or password');
                        }
                    }, 1000);
                }
            });

            // Forgot password functionality
            forgotPasswordLink.addEventListener('click', function (e) {
                e.preventDefault();
                const userEmail = prompt('Please enter your email address:');
                if (userEmail && isValidEmail(userEmail)) {
                    alert('Password reset link has been sent to ' + userEmail);
                } else if (userEmail) {
                    alert('Please enter a valid email address');
                }
            });

            // Demo credentials hint
            setTimeout(() => {
                const hint = document.createElement('div');
                hint.style.cssText = `
                    position: fixed;
                    top: 20px;
                    right: 20px;
                    background: rgba(0, 0, 0, 0.8);
                    color: white;
                    padding: 10px 15px;
                    border-radius: 8px;
                    font-size: 12px;
                    z-index: 1000;
                `;
                hint.innerHTML = 'Demo: demo@example.com / password';
                document.body.appendChild(hint);

                setTimeout(() => {
                    hint.remove();
                }, 5000);
            }, 2000);
        });
    </script> -->
            </body>

            </html>